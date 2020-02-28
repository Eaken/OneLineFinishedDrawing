package com.example.onelinetoend.View.Fragment;

import android.widget.Button;

import com.example.onelinetoend.R;
import com.example.onelinetoend.Util.ActivityUtil;
import com.example.onelinetoend.Util.ValueUtil;
import com.example.onelinetoend.Util.ViewUtil;
import com.example.onelinetoend.View.Activity.MainActivity;

import butterknife.BindView;

public class IndexFragment extends BaseFragment {

    @BindView(R.id.btPrimary)
    Button btPrimary;

    @BindView(R.id.btRandom)
    Button btRandom;

    @BindView(R.id.btSetting)
    Button btSetting;

    @BindView(R.id.btFinsh)
    Button btFinsh;

    @Override
    public int getLayoutId() {
        return R.layout.layout_index;
    }

    @Override
    public boolean initView() {
        btPrimary.setOnClickListener(v -> {
            if(getActivity() instanceof MainActivity){
                ((MainActivity)getActivity()).startFragment(DifficultyFragment.class,0,null,null);
            }else {
                showToast("跳转失败，请联系开发人员");
            }
        });
        btRandom.setOnClickListener(v -> {
            if(getActivity() instanceof MainActivity){
                ((MainActivity)getActivity()).startFragment(RandomRoadFragment.class,0,null,null);
            }else {
                showToast("跳转失败，请联系开发人员");
            }
        });
        btSetting.setOnClickListener(v-> ViewUtil.getSettingDialog(getPreferencesEditor()));
        btFinsh.setOnClickListener(v -> ActivityUtil.getInstance().finishActivity());
        return true;
    }
}
