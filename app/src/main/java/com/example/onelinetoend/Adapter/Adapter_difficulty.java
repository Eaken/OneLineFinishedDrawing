package com.example.onelinetoend.Adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.example.onelinetoend.Model.Bean.Bean_Road;
import com.example.onelinetoend.R;
import com.example.onelinetoend.Util.ActivityUtil;
import com.example.onelinetoend.Util.ValueUtil;
import com.example.onelinetoend.View.Activity.MainActivity;
import com.example.onelinetoend.View.Fragment.DifficultyDetailFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter_difficulty extends RecyclerView.Adapter {

    private List<List<Bean_Road>> roadValuesList;

    public Adapter_difficulty(List<List<Bean_Road>> roadValuesList){
        this.roadValuesList = roadValuesList;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_difficulty,parent,false)){};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        Button button = holder.itemView.findViewById(R.id.itemdifficulty);
        button.setText("关卡"+(position+1));
        button.setOnClickListener(v->{
            final Activity activity = ActivityUtil.getInstance().getCurActivity();
            if(activity instanceof MainActivity){
                Bundle bundle = new Bundle();
                bundle.putInt(ValueUtil.Cur_Detail_Position,position);
                ((MainActivity)activity).startFragment(DifficultyDetailFragment.class,0,bundle,null);
            }else {
                button.post(()->Toast.makeText(button.getContext(),"跳转失败，请联系管理员",Toast.LENGTH_SHORT).show());
            }
        });
    }

    @Override
    public int getItemCount() {
        return roadValuesList.size();
    }
}
