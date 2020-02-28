package com.example.onelinetoend.View.Fragment;

import android.view.View;
import android.widget.TextView;

import com.example.onelinetoend.Adapter.Adapter_difficulty;
import com.example.onelinetoend.R;
import com.example.onelinetoend.Util.RoadValuesUtil;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import butterknife.BindView;

public class DifficultyFragment extends BaseFragment {
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
        returnButton.setOnClickListener(v -> onBackClick());
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new Adapter_difficulty(RoadValuesUtil.roadValuesList));
        difficultyHint.setVisibility(View.GONE);
        return true;
    }
}
