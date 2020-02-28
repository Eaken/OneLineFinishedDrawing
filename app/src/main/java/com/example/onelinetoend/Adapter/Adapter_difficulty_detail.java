package com.example.onelinetoend.Adapter;

import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.onelinetoend.Model.Bean.Bean_Road;
import com.example.onelinetoend.Model.MySql;
import com.example.onelinetoend.R;
import com.example.onelinetoend.Util.ActivityUtil;
import com.example.onelinetoend.Util.RoadValuesUtil;
import com.example.onelinetoend.Util.ValueUtil;
import com.example.onelinetoend.View.Activity.MainActivity;
import com.example.onelinetoend.View.Fragment.RoadFragment;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.core.widget.TextViewCompat;
import androidx.recyclerview.widget.RecyclerView;

public class Adapter_difficulty_detail extends RecyclerView.Adapter {

    private List<Bean_Road> roadList;
    private MySql mySql;
    private int curDetailPosition;

    public Adapter_difficulty_detail(int curDetailPosition){
        this.roadList = RoadValuesUtil.roadValuesList.get(curDetailPosition);
        this.curDetailPosition = curDetailPosition;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new RecyclerView.ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_difficulty_detail,parent,false)){};
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        if(mySql == null) mySql=new MySql(holder.itemView.getContext());
        TextView textView = holder.itemView.findViewById(R.id.itemDifficultyDetail);
        TextViewCompat.setAutoSizeTextTypeWithDefaults(textView, TextViewCompat.AUTO_SIZE_TEXT_TYPE_UNIFORM);
        textView.setText(""+(position+1));
        if(mySql.checkPassedYibi(roadList.get(position))){
            textView.setBackgroundResource(R.drawable.shape_gray_selected);
        }else {
            textView.setBackgroundResource(R.drawable.shape_gray_unselected);
        }
        textView.setOnClickListener(v->{
            final Activity activity = ActivityUtil.getInstance().getCurActivity();
            if(activity instanceof MainActivity){
                Bundle bundle = new Bundle();
                bundle.putInt(ValueUtil.Cur_Detail_Position,curDetailPosition);
                bundle.putInt(ValueUtil.Cur_Road_Position,position);
                ((MainActivity)activity).startFragment(RoadFragment.class,0,bundle,null);
            }else {
                textView.post(()->Toast.makeText(textView.getContext(),"跳转失败，请联系管理员",Toast.LENGTH_SHORT).show());
            }
        });
    }

    @Override
    public int getItemCount() {
        return roadList.size();
    }
}
