package com.example.onelinetoend.View.Fragment;

import android.content.Intent;
import android.view.View;
import android.widget.TextView;

import com.example.onelinetoend.Adapter.Adapter_difficulty_detail;
import com.example.onelinetoend.R;
import com.example.onelinetoend.Util.RoadValuesUtil;
import com.example.onelinetoend.Util.ValueUtil;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class DifficultyDetailFragment extends BaseFragment {

    @BindView(R.id.difficultyHint)
    TextView difficultyHint;

    @BindView(R.id.returnButton)
    View returnButton;

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    public int getLayoutId() {
        return R.layout.layout_difficulty;
    }

    @Override
    public boolean initView() {
        int position = getNonNullArguments().getInt(ValueUtil.Cur_Detail_Position,0);
        returnButton.setOnClickListener(v -> onBackClick());
        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),5));
        if(position>=0 && position<RoadValuesUtil.roadValuesList.size()){
            recyclerView.setAdapter(new Adapter_difficulty_detail(position));
        }else {
            showToast("获取地图失败!关卡:"+(position+1));
        }
        difficultyHint.setVisibility(View.VISIBLE);
        difficultyHint.setText("关卡"+(position+1));
        return true;
    }

    @Override
    public void onNewArguments() {
        super.onNewArguments();
        isLoaded = initView();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(data!=null&&data.getExtras()!=null) getNonNullArguments().putAll(data.getExtras());
        runOnUiThread(this::initView);
    }
}
