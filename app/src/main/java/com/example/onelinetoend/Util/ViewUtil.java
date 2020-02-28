package com.example.onelinetoend.Util;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.transition.Fade;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.ListView;
import android.widget.NumberPicker;
import android.widget.ProgressBar;
import android.widget.Switch;
import android.widget.TextView;

import com.example.onelinetoend.Adapter.Adapter_Yibi_Collection;
import com.example.onelinetoend.Model.Bean.Bean_Collection;
import com.example.onelinetoend.R;
import com.example.onelinetoend.Util.Services.MusicService;
import com.example.onelinetoend.View.Fragment.BaseFragment;

import java.lang.reflect.Field;
import java.util.List;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

public class ViewUtil {

    public static  <t extends BaseFragment> void startFragment(@NonNull FragmentManager fragmentManager,Fragment currentFragment,@IdRes int containerId, @NonNull Class<t> fragmentClass, int requestCode, @Nullable Bundle bundle, @Nullable List<View> shareElements){
        int launchMode = ValueUtil.getFragmentLaunchMode(fragmentClass);
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        if(launchMode==ValueUtil.Fragment_LaunchModel_SingleTask){
            List<Fragment> fragments = fragmentManager.getFragments();
            for(int i = 0, n = fragments.size();i<n;i++){
                final Fragment fragment = fragments.get(i);
                if(fragmentClass.isInstance(fragment)){
                    for(int j = i+1;j<n;j++){
                        final Fragment topFragment = fragments.get(j);
                        topFragment.setSharedElementEnterTransition(null);
                        topFragment.setSharedElementReturnTransition(null);
                        topFragment.setEnterTransition(null);
                        topFragment.setExitTransition(null);
                        transaction.remove(topFragment);
                    }

                    transaction.setCustomAnimations(R.anim.scene_open_enter,R.anim.scene_open_exit)
                            .show(fragment)
                            .commitAllowingStateLoss();
                    if(bundle==null) bundle=new Bundle();
                    bundle.putInt(ValueUtil.Fragment_Reqest_Code,requestCode);
                    fragment.setArguments(bundle);
                    if(fragment instanceof BaseFragment){
                        ((BaseFragment)fragment).onNewArguments();
                    }
                    return;
                }
            }
        }

        if(launchMode == ValueUtil.Fragment_LaunchModel_SingleTop && fragmentClass.isInstance(currentFragment)){
            if(bundle==null) bundle=new Bundle();
            bundle.putInt(ValueUtil.Fragment_Reqest_Code,requestCode);
            currentFragment.setArguments(bundle);
            if(currentFragment instanceof BaseFragment){
                ((BaseFragment)currentFragment).onNewArguments();
            }
            return;
        }

        BaseFragment newFragment = t.newInstance(fragmentClass,bundle,requestCode);
        if(currentFragment!=null){
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
                newFragment.setEnterTransition(new Fade());
                newFragment.setExitTransition(new Fade());
                currentFragment.setSharedElementReturnTransition(AnimUtil.getScaleTransition());
                newFragment.setSharedElementEnterTransition(AnimUtil.getScaleTransition());
            }else {
                currentFragment.setEnterTransition(null);
                currentFragment.setExitTransition(null);
                newFragment.setEnterTransition(null);
                newFragment.setExitTransition(null);
                currentFragment.setSharedElementReturnTransition(null);
                newFragment.setSharedElementEnterTransition(null);
                transaction.setCustomAnimations(R.anim.scene_open_enter,R.anim.scene_open_exit);
            }
            transaction.hide(currentFragment);
        }

        transaction.add(containerId, newFragment).commitAllowingStateLoss();
    }


    public interface ShareElementsListener{
        @Nullable List<View> getShareElements();
    }

    public static AlertDialog getMyDialog(@NonNull boolean[] checks, @NonNull String[] strings, DialogInterface.OnClickListener listener) {
        final Activity context = ActivityUtil.getInstance().getCurActivity();
        boolean showCheck = checks.length>0 && checks.length==strings.length;
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        if(showCheck) {
            builder.setMultiChoiceItems(strings, checks, (dialog, which, isChecked) -> checks[which] = isChecked);
        }else if(strings.length>0){
            builder.setItems(strings,listener);
        }
        AlertDialog dialog = builder.create();
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(ContextCompat.getDrawable(context, R.drawable.background_shape_white));
            window.setWindowAnimations(R.style.scale_anim);
            window.clearFlags(WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM);
            window.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE
                    | WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        }
        return dialog;
    }

    public static AlertDialog getMyDialog(){
        return getMyDialog(new boolean[0],new String[0],null);
    }

    public static AlertDialog getMyDialog(String[] strings, DialogInterface.OnClickListener listener){
        return getMyDialog(new boolean[0],strings,listener);
    }

    public static AlertDialog getWaittingDialog(){
        Activity context = ActivityUtil.getInstance().getCurActivity();

        final AlertDialog dialog=getMyDialog();
        dialog.setCanceledOnTouchOutside(false);

        View waittingView= LayoutInflater.from(context).inflate(R.layout.layout_waiting,null);

        ProgressBar progressBar=waittingView.findViewById(R.id.progress);
        progressBar.getIndeterminateDrawable().setColorFilter(ContextCompat.getColor(context,R.color.colorGray), PorterDuff.Mode.MULTIPLY);

        Button qx=waittingView.findViewById(R.id.qx);
        qx.setOnClickListener(v -> dialog.dismiss());

        final TextView hint=waittingView.findViewById(R.id.hint);

        dialog.setOnShowListener(dialog1 -> {
            hint.clearAnimation();
            hint.setVisibility(View.VISIBLE);
            hint.postDelayed(()->AnimUtil.doScaleAnimation(hint, 1, 0, 1, 0, false, new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {

                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    hint.setVisibility(View.GONE);
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            }, true),3000);
        });

        dialog.show();
        dialog.setContentView(waittingView);
        return dialog;
    }

    public static View getSelectorView(){
        Activity context = ActivityUtil.getInstance().getCurActivity();

        AlertDialog dialog=getMyDialog();

        View view=LayoutInflater.from(context).inflate(R.layout.layout_selector_yibi,null);

        NumberPicker row=view.findViewById(R.id.rowPicker);
        setDividerColor(row);
        NumberPicker column=view.findViewById(R.id.columnPicker);
        setDividerColor(column);
        NumberPicker forbidden=view.findViewById(R.id.forbiddenPicker);
        setDividerColor(forbidden);

        dialog.show();
        dialog.setContentView(view);
        view.setTag(dialog);
        return view;
    }

    private static void setDividerColor(NumberPicker picker) {
        try {
            Field field=NumberPicker.class.getDeclaredField("mSelectionDivider");
            field.setAccessible(true);
            field.set(picker,new ColorDrawable(Color.TRANSPARENT));
        }catch (Exception e){
            OtherUtil.DebuggerLog(e);
        }
    }

    public static AlertDialog getAskDialog(String title, String content, OtherUtil.OnCallBackListenerImpl<Boolean> listener, String... texts) {
        String qdText = texts.length>0?texts[0]:"确定";
        String qxText = texts.length>1?texts[1]:"取消";
        String qtText = texts.length>2?texts[2]:"";
        String checkText = texts.length>3?texts[3]:"";
        final boolean[] checkStaus;
        if(ValueUtil.isNotEmpty(checkText)){
            checkStaus = new boolean[1];
        }else {
            checkStaus = new boolean[0];
        }
        AlertDialog dialog = getMyDialog(checkStaus,new String[]{checkText},null);
        if(checkStaus.length>0){
            dialog.setTitle(content);
        }else {
            if(ValueUtil.isNotEmpty(title)) dialog.setTitle(title);
            dialog.setMessage(content);
        }
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, qdText, (dialog13, which) -> {
            Boolean check = checkStaus.length > 0 && checkStaus[0];
            listener.OnCallBackFirst(check);
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, qxText, (dialog12, which) -> {
            Boolean check = checkStaus.length > 0 && checkStaus[0];
            listener.OnCallBackSecond(check);
        });
        if (ValueUtil.isNotEmpty(qtText)) {
            dialog.setButton(DialogInterface.BUTTON_NEUTRAL, qtText, (dialog1, which) -> {
                Boolean check = checkStaus.length > 0 && checkStaus[0];
                listener.OnCallBackThrid(check);
            });
        }
        dialog.show();
        return dialog;
    }

    public static AlertDialog getCollectionDialog(List<Bean_Collection> collections,View.OnClickListener onClickListener){
        Activity context = ActivityUtil.getInstance().getCurActivity();
        final AlertDialog dialog=getMyDialog();
        View view=LayoutInflater.from(context).inflate(R.layout.layout_collection,null);
        ListView listView=view.findViewById(R.id.list_collection);
        listView.setAdapter(new Adapter_Yibi_Collection(collections));
        View clean=view.findViewById(R.id.clean);
        clean.setOnClickListener(onClickListener);
        dialog.show();
        dialog.setContentView(view);
        return dialog;
    }

    public static AlertDialog getSettingDialog(@NonNull SharedPreferences.Editor editor){
        Activity context = ActivityUtil.getInstance().getCurActivity();
        final AlertDialog dialog=getMyDialog();
        View view=LayoutInflater.from(context).inflate(R.layout.layout_setting,null);
        Switch switchMusic = view.findViewById(R.id.switchMusic);
        Switch sqlSwitch = view.findViewById(R.id.switchSql);

        switchMusic.setChecked(ValueUtil.toPlayMusic);
        switchMusic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            ValueUtil.toPlayMusic = isChecked;
            editor.putBoolean(ValueUtil.key_toPlayMusic,ValueUtil.toPlayMusic);
            editor.apply();
            //启动服务，播放音乐
            Intent intent=new Intent(context, MusicService.class);
            intent.putExtra(ValueUtil.key_toPlayMusic,ValueUtil.toPlayMusic?0:2);
            context.startService(intent);
        });

        sqlSwitch.setChecked(ValueUtil.toFindRoadToSql);
        sqlSwitch.setOnCheckedChangeListener((buttonView, isChecked) -> {
            ValueUtil.toFindRoadToSql = isChecked;
            editor.putBoolean(ValueUtil.key_toFindRoadToSql,ValueUtil.toFindRoadToSql);
            editor.apply();
            if(ValueUtil.toFindRoadToSql){
                ValueUtil.findRanRoadToSql(context);
            }
        });
        dialog.show();
        dialog.setContentView(view);
        return dialog;
    }


    public static boolean isServiceExisted(Context context, String className) {
        try {
            ActivityManager activityManager = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningServiceInfo> serviceList = activityManager
                    .getRunningServices(Integer.MAX_VALUE);

            if (!(serviceList.size() > 0)) {
                return false;
            }

            for (int i = 0; i < serviceList.size(); i++) {
                ActivityManager.RunningServiceInfo serviceInfo = serviceList.get(i);
                ComponentName serviceName = serviceInfo.service;

                if (serviceName.getClassName().equals(className)) {
                    return true;
                }
            }
        }catch (Exception e){
            OtherUtil.DebuggerLog(e);
        }
        return false;
    }

    public static boolean isContextExisted(Context context) {
        if (context instanceof Activity) {
            return !((Activity) context).isFinishing();
        } else if (context instanceof Service) {
            return isServiceExisted(context, context.getClass().getName());
        } else return context instanceof Application;
    }

}
