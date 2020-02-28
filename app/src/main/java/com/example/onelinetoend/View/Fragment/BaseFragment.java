package com.example.onelinetoend.View.Fragment;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.onelinetoend.Model.MySql;
import com.example.onelinetoend.R;
import com.example.onelinetoend.Util.ActivityUtil;
import com.example.onelinetoend.Util.OtherUtil;
import com.example.onelinetoend.Util.ThreadUtil;
import com.example.onelinetoend.Util.ValueUtil;
import com.example.onelinetoend.Util.ViewUtil;
import com.example.onelinetoend.View.Activity.BaseActivity;

import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

import androidx.annotation.IdRes;
import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class BaseFragment extends Fragment implements BaseActivity.OnFragmentBackPressedListener, ViewUtil.ShareElementsListener {

    private ConcurrentLinkedQueue<Runnable> runnableQueue = new ConcurrentLinkedQueue<>();

    protected OnBackClickListener onBackClickListener;

    protected boolean isLoaded = false;

    protected Unbinder unbinder;
    private final Handler mhandler = new Handler();
    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;
    private MySql mySql;

    private int requestCode = ValueUtil.Default_Unused_Code,resultCode = ValueUtil.Default_Unused_Code;
    private Intent resultData;

    public void setRequestCode(int requestCode) {
        this.requestCode = requestCode;
    }

    public void setResult(int resultCode){
        setResult(resultCode,null);
    }

    public void setResult(int resultCode, Intent resultData) {
        this.resultCode = resultCode;
        this.resultData = resultData;
    }

    public int getRequestCode() {
        return requestCode;
    }

    public int getResultCode() {
        return resultCode;
    }

    public Intent getResultData() {
        return resultData;
    }

    public BaseFragment() {}

    public static BaseFragment newInstance(Class<? extends BaseFragment> fraggemntClass,Bundle bundle,int requestCode){
        try {
            if(requestCode!=ValueUtil.Default_Unused_Code){
                if(bundle==null) bundle=new Bundle();
                bundle.putInt(ValueUtil.Fragment_Reqest_Code,requestCode);
            }
            BaseFragment fragment = fraggemntClass.newInstance();
            fragment.setArguments(bundle);
            fragment.setEnterTransition(new Fade());
            fragment.setExitTransition(new Fade());
            return fragment;
        }catch (Exception e){
            throw new RuntimeException(e);
        }
    }

    public void onNewArguments(){
        setRequestCode(getNonNullArguments().getInt(ValueUtil.Fragment_Reqest_Code,ValueUtil.Default_Unused_Code));
    }


    public void addRunnableToQueue(@NonNull Runnable runnable){
        runnableQueue.offer(runnable);
    }

    public void runOnUiThread(Runnable runnable) {
        postRunnaleQueue();
        mhandler.post(() -> {
            if(!isLoaded) return;
            runnable.run();
        });
    }

    public void runOnUiThread(Runnable runnable, long delay) {
        postRunnaleQueue();
        mhandler.postDelayed(() -> {
            if(!isLoaded) return;
            runnable.run();
        },delay);
    }

    public void runOnChildThread(Runnable runnable) {
        postRunnaleQueue();
        ThreadUtil.getInstance().runOnChildThread(()->{
            if(!isLoaded) return;
            runnable.run();
        });
    }

    private void postRunnaleQueue(){
        Runnable r;
        while ((r=runnableQueue.poll())!=null){
            r.run();
        }
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if(!hidden){
            postRunnaleQueue();
        }
    }

    @Nullable
    @Override
    public List<View> getShareElements() {
        return null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity() instanceof BaseActivity){
            ((BaseActivity)getActivity()).setOnFragmentBackPressedListener(this);
        }
        if(!isLoaded){
            mhandler.post(()->{
                if(getView()!=null){
                    try {
                        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
                        for(Fragment f : getChildFragmentManager().getFragments()){
                            transaction.remove(f);
                        }
                        transaction.commitNowAllowingStateLoss();
                    }catch (Exception e){
                        OtherUtil.DebuggerLog(e);
                    }
                    createPreferences();
                    getView().setBackgroundColor(ContextCompat.getColor(ActivityUtil.getInstance().getCurActivity(), R.color.colorPrimary));
                    unbinder = ButterKnife.bind(BaseFragment.this,getView());
                    isLoaded = initView();
                    setRequestCode(getNonNullArguments().getInt(ValueUtil.Fragment_Reqest_Code,ValueUtil.Default_Unused_Code));
                }
            });
        }
        postRunnaleQueue();
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if(savedInstanceState!=null) getNonNullArguments().putAll(savedInstanceState);
        return inflater.inflate(getLayoutId(),container,false);
    }

    public MySql getMySql() {
        if(mySql==null){
            mySql=new MySql(getContext());
        }
        return mySql;
    }

    public @NonNull
    Bundle getNonNullArguments(){
        Bundle bundle = getArguments();
        if(bundle==null){
            bundle = new Bundle();
            setArguments(bundle);
        }
        return getArguments();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        if(getArguments()!=null){
            outState.putAll(getArguments());
        }
        super.onSaveInstanceState(outState);
    }

    public abstract @LayoutRes int getLayoutId();

    public abstract boolean initView();

    public  <V extends View> V findViewById(@IdRes int id){
        if(getView()==null) return null;
        return getView().findViewById(id);
    }

    public void showToast(String msg) {
        runOnUiThread(() -> Toast.makeText(getContext(),msg, Toast.LENGTH_SHORT).show());
    }

    @Override
    public void onDestroyView() {
        isLoaded = false;
        if(unbinder!=null){
            unbinder.unbind();
            unbinder = null;
        }
        preferences = null;
        preferencesEditor = null;
        super.onDestroyView();
    }


    @NonNull
    @Override
    public Context getContext() {
        if(super.getContext()!=null)
            return super.getContext();
        if(getActivity()!=null) return getActivity();
        return ActivityUtil.getInstance().getCurActivity();
    }


    @Override
    public boolean onBackPressed() {
        return false;
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if(context instanceof OnBackClickListener){
            onBackClickListener = (OnBackClickListener)context;
        }
    }

    public void onBackClick(){
        if(onBackClickListener!=null){
            onBackClickListener.onBackClick();
        }
    }


    public boolean isLoaded() {
        return isLoaded;
    }


    protected void createPreferences(){
        preferences = getContext().getSharedPreferences(ValueUtil.appName, Context.MODE_PRIVATE);
        preferencesEditor = preferences.edit();
    }

    @NonNull
    public SharedPreferences.Editor getPreferencesEditor(){
        if(preferencesEditor == null){
            createPreferences();
        }
        return preferencesEditor;
    }

    public SharedPreferences getPreferences() {
        if(preferences == null){
            createPreferences();
        }
        return preferences;
    }

    public interface OnBackClickListener{
        void onBackClick();
    }

}
