package com.example.onelinetoend.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.example.onelinetoend.Model.Bean.Bean_Road;
import com.example.onelinetoend.R;
import com.example.onelinetoend.Util.ValueUtil;

import java.util.List;

public class Adapter_Yibi extends BaseAdapter {

    private final int size;
    private final int startPosition;
    private final List<Integer> road;

    public Adapter_Yibi(Bean_Road bean_road){
        this.road=bean_road.getRoadList();
        if(ValueUtil.isListEmpty(road)){
            this.size=0;
            this.startPosition=0;
        }else {
            this.size=bean_road.getRows()*bean_road.getColumns();
            this.startPosition=road.get(0);
        }
    }


    @Override
    public int getCount() {
        return size;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if(convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_yibi,parent,false).findViewById(R.id.parentyibi);
        }
        if(startPosition==position){
            convertView.findViewById(R.id.baseyibi).setBackgroundResource(R.drawable.shape_gray_deep_selected);
        }
        boolean isAllowed=false;
        for(int p:road){
            if(p==position){
                isAllowed=true;
            }
        }
        if(!isAllowed){
            convertView.setTag("forbidden");
            convertView.findViewById(R.id.baseyibi).setBackgroundResource(R.color.colortransparency);
        }
        return convertView;
    }

}
