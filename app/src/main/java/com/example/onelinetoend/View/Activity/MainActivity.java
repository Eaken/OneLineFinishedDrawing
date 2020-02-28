package com.example.onelinetoend.View.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.onelinetoend.R;
import com.example.onelinetoend.Util.Services.MusicService;
import com.example.onelinetoend.Util.ThreadUtil;
import com.example.onelinetoend.Util.ValueUtil;
import com.example.onelinetoend.Util.ViewUtil;
import com.example.onelinetoend.View.Activity.BaseActivity;
import com.example.onelinetoend.View.Fragment.BaseFragment;
import com.example.onelinetoend.View.Fragment.IndexFragment;

import java.util.List;

public class MainActivity extends BaseActivity implements BaseFragment.OnBackClickListener{

    public <t extends BaseFragment> void startFragment(@NonNull Class<t> fragmentClass ,int requestCode,  @Nullable Bundle bundle, @Nullable List<View> shareElements) {
        ViewUtil.startFragment(getSupportFragmentManager(),getCurrentFragment(),R.id.fragmentLayout, fragmentClass, requestCode, bundle, shareElements);
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        ValueUtil.toFindRoadToSql = getPreferences().getBoolean(ValueUtil.key_toFindRoadToSql,true);
        ValueUtil.toPlayMusic = getPreferences().getBoolean(ValueUtil.key_toPlayMusic,true);
        if(ValueUtil.toFindRoadToSql) ValueUtil.findRanRoadToSql(this);
        final List<Fragment> fragments = getSupportFragmentManager().getFragments();
        if(ValueUtil.isListEmpty(fragments)){
            startFragment(IndexFragment.class,0,null,null);
        }else {
            boolean hasIndex = false;
            for(Fragment fragment : fragments){
                if(fragment instanceof IndexFragment){
                    hasIndex = true;
                    break;
                }
            }
            if(!hasIndex){
                startFragment(IndexFragment.class,0,null,null);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Intent intent = new Intent(this, MusicService.class);
        intent.putExtra(ValueUtil.key_toPlayMusic,ValueUtil.toPlayMusic?0:2);
        startService(intent);
    }

    @Override
    protected void onPause() {
        super.onPause();
        Intent intent = new Intent(this, MusicService.class);
        intent.putExtra(ValueUtil.key_toPlayMusic,1);
        startService(intent);
    }

    @Override
    protected void onDestroy() {
        stopService(new Intent(this, MusicService.class));
        ThreadUtil.getInstance().removeRunable("findRoadToSql");
        super.onDestroy();
    }

    @Override
    public boolean isNeedCleanFragments() {
        return false;
    }

    @Override
    public void onBackClick() {
        onBackPressed();
    }
}
