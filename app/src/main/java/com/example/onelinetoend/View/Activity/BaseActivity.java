package com.example.onelinetoend.View.Activity;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.PersistableBundle;
import android.transition.Fade;
import android.view.View;
import android.view.Window;
import android.widget.Toast;

import com.example.onelinetoend.R;
import com.example.onelinetoend.Util.ActivityUtil;
import com.example.onelinetoend.Util.AnimUtil;
import com.example.onelinetoend.Util.OtherUtil;
import com.example.onelinetoend.Util.ThreadUtil;
import com.example.onelinetoend.Util.ValueUtil;
import com.example.onelinetoend.Util.ViewUtil;
import com.example.onelinetoend.View.Fragment.BaseFragment;

import java.util.List;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public abstract class BaseActivity extends AppCompatActivity {

    private Unbinder unbinder;
    private final Handler mhandler = new Handler();
    private long lastBackClickTime = 0;
    private SharedPreferences preferences;
    private SharedPreferences.Editor preferencesEditor;

    @Override
    public void onSaveInstanceState(Bundle outState, PersistableBundle outPersistentState) {
        super.onSaveInstanceState(outState, outPersistentState);
        if(getIntent()!=null && getIntent().getExtras()!=null) outState.putAll(getIntent().getExtras());
    }

    public @NonNull
    Intent getNonNullIntent(){
        Intent intent = getIntent();
        if(intent==null){
            intent = new Intent();
            setIntent(intent);
        }
        return getIntent();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        if(savedInstanceState!=null){
            getNonNullIntent().putExtras(savedInstanceState);
        }
        createPreferences();
        // 设置一个exit transition
        getWindow().requestFeature(Window.FEATURE_CONTENT_TRANSITIONS);
        getWindow().setEnterTransition(new Fade());
        getWindow().setExitTransition(new Fade());
        getApplication().registerActivityLifecycleCallbacks(new Application.ActivityLifecycleCallbacks() {
            @Override
            public void onActivityCreated(@NonNull Activity activity, @Nullable Bundle savedInstanceState) {
                ActivityUtil.getInstance().addActivity(activity);
            }

            @Override
            public void onActivityStarted(@NonNull Activity activity) {

            }

            @Override
            public void onActivityResumed(@NonNull Activity activity) {
                ActivityUtil.getInstance().setCurActivity(activity);
            }

            @Override
            public void onActivityPaused(@NonNull Activity activity) {

            }

            @Override
            public void onActivityStopped(@NonNull Activity activity) {

            }

            @Override
            public void onActivitySaveInstanceState(@NonNull Activity activity, @NonNull Bundle outState) {

            }

            @Override
            public void onActivityDestroyed(@NonNull Activity activity) {
                ActivityUtil.getInstance().removeActivity(activity);
            }
        });
        super.onCreate(savedInstanceState);
        setContentView(getLayoutId());
        getWindow().getDecorView().setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.colorPrimary));
        unbinder = ButterKnife.bind(this);
        if(isNeedCleanFragments()) cleanFragments();
        initView(savedInstanceState);
    }

    public boolean isNeedCleanFragments(){
        return true;
    }

    protected void cleanFragments(){
        try {
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            for(Fragment f : getSupportFragmentManager().getFragments()){
                transaction.remove(f);
            }
            transaction.commitNowAllowingStateLoss();
        }catch (Exception e){
            OtherUtil.DebuggerLog(e);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if(unbinder!=null){
            unbinder.unbind();
            unbinder=null;
        }
        preferences = null;
        preferencesEditor = null;
    }

    /**
     * 设置布局id
     *
     * @return layoutid
     */
    public abstract @LayoutRes
    int getLayoutId();

    /**
     * 初始化视图
     */
    public abstract void initView(@Nullable Bundle savedInstanceState);

    public void showToast(String msg) {
        runOnUiThread(() -> Toast.makeText(getApplicationContext(),msg, Toast.LENGTH_SHORT).show());
    }

    public void runOnUiThread(Runnable runnable, long delay) {
        mhandler.postDelayed(runnable,delay);
    }

    public void runOnChildThread(Runnable runnable) {
        ThreadUtil.getInstance().runOnChildThread(runnable);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        final Fragment fragment = getCurrentFragment();
        if(fragment instanceof BaseFragment && fragment.isVisible()) {
            if (((BaseFragment)fragment).isLoaded()) {
                fragment.onActivityResult(requestCode,resultCode, data);
            }
        }else if(fragment!=null && fragment.isResumed()){
            fragment.onActivityResult(requestCode,resultCode, data);
        }
        super.onActivityResult(requestCode, resultCode, data);
    }


    public interface OnFragmentBackPressedListener{
        boolean onBackPressed();
    }

    protected OnFragmentBackPressedListener onFragmentBackPressedListener;

    public void setOnFragmentBackPressedListener(OnFragmentBackPressedListener listener){
        onFragmentBackPressedListener = listener;
    }

    @Override
    public void onBackPressed() {
        if(onFragmentBackPressedListener!=null){
            if(onFragmentBackPressedListener.onBackPressed()){
                return;
            }
        }
        if(popbackFragment()){
            return;
        }
        if(needDoubleClickToBack()){
            final long nowTime = System.currentTimeMillis();
            if(nowTime-lastBackClickTime>1500){
                lastBackClickTime = nowTime;
                showToast("再按一次后退键退出");
                return;
            }
        }
        super.onBackPressed();
    }

    protected boolean popbackFragment(){
        final int fSize = getSupportFragmentManager().getFragments().size();
        if(fSize>=2){
            Fragment currentFragment = getCurrentFragment();
            Fragment secondFragment = getSecondFragment();
            if(currentFragment==null || secondFragment==null){
                return false;
            }
            FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
            if(currentFragment instanceof BaseFragment && secondFragment instanceof BaseFragment){
                BaseFragment cf = (BaseFragment)currentFragment;
                if(cf.getRequestCode()!=ValueUtil.Default_Unused_Code && cf.getResultCode()!=ValueUtil.Default_Unused_Code){
                    ((BaseFragment)secondFragment).addRunnableToQueue(()-> secondFragment.onActivityResult(cf.getRequestCode(),cf.getResultCode(),cf.getResultData()));
                }
            }
            if(currentFragment instanceof ViewUtil.ShareElementsListener){
                final List<View> shareElements = ((ViewUtil.ShareElementsListener)currentFragment).getShareElements();
                boolean hasShareElement = false;
                if(ValueUtil.isListNotEmpty(shareElements)){
                    for (View shareElement : shareElements){
                        if(shareElement!=null && shareElement.getTransitionName()!=null){
                            hasShareElement = true;
                            transaction.addSharedElement(shareElement,shareElement.getTransitionName());
                        }
                    }
                }
                if(hasShareElement){
                    currentFragment.setEnterTransition(new Fade());
                    currentFragment.setExitTransition(new Fade());
                    secondFragment.setEnterTransition(new Fade());
                    secondFragment.setExitTransition(new Fade());
                    currentFragment.setSharedElementReturnTransition(AnimUtil.getScaleTransition());
                    secondFragment.setSharedElementEnterTransition(AnimUtil.getScaleTransition());
                }else {
                    currentFragment.setEnterTransition(null);
                    currentFragment.setExitTransition(null);
                    secondFragment.setEnterTransition(null);
                    secondFragment.setExitTransition(null);
                    currentFragment.setSharedElementReturnTransition(null);
                    secondFragment.setSharedElementEnterTransition(null);
                    transaction.setCustomAnimations(R.anim.scene_close_enter,R.anim.scene_close_exit);
                }
            }
            transaction.remove(currentFragment)
                    .show(secondFragment)
                    .commitAllowingStateLoss();
            return true;
        }
        return false;
    }

    protected boolean needDoubleClickToBack(){
        return true;
    }

    public Fragment getCurrentFragment(){
        Fragment currentFragment = null;
        final List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (int p=fragments.size()-1;p>=0;p--){
            Fragment f = fragments.get(p);
            if(f!=null && f.isVisible() && f.isResumed() && !f.isDetached()){
                currentFragment = f;
                break;
            }
        }
        return currentFragment;
    }

    protected Fragment getSecondFragment(){
        Fragment currentFragment = getCurrentFragment();
        if(currentFragment==null) return null;
        Fragment secondFragment = null;
        final List<Fragment> fragments = getSupportFragmentManager().getFragments();
        for (int p=fragments.size()-1;p>=0;p--){
            Fragment f = fragments.get(p);
            if(f!=null && f!=currentFragment){
                secondFragment = f;
                break;
            }
        }
        return secondFragment;
    }

    private void createPreferences(){
        preferences = getSharedPreferences(ValueUtil.appName, Context.MODE_PRIVATE);
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

}
