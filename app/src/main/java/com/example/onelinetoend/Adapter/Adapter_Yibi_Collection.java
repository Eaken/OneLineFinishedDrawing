package com.example.onelinetoend.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.example.onelinetoend.Model.Bean.Bean_Collection;
import com.example.onelinetoend.R;

import java.util.List;

public class Adapter_Yibi_Collection extends BaseAdapter {

    private List<Bean_Collection> collections;

    public Adapter_Yibi_Collection(List<Bean_Collection> collections) {
        this.collections = collections;
        if(collections.size()==0){
            collections.add(new Bean_Collection(0,0,0));
        }
    }

    @Override
    public int getCount() {
        return collections.size();
    }

    @Override
    public Object getItem(int position) {
        return collections.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    class ViewHolder{
        TextView rows;
        TextView columns;
        TextView difficulties;
        TextView passedSum;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if(convertView==null){
            convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_yibi_collection,null);
            viewHolder=new ViewHolder();
            viewHolder.columns=convertView.findViewById(R.id.columns);
            viewHolder.rows=convertView.findViewById(R.id.rows);
            viewHolder.difficulties=convertView.findViewById(R.id.difficulties);
            viewHolder.passedSum=convertView.findViewById(R.id.passedSum);
            convertView.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)convertView.getTag();
        }
        viewHolder.passedSum.setText(collections.get(position).getSum()+"");
        viewHolder.columns.setText(collections.get(position).getColumns()+"");
        viewHolder.rows.setText(collections.get(position).getRows()+"");
        viewHolder.difficulties.setText(collections.get(position).getDifficulties()+"");
        return convertView;
    }
}
