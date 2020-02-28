package com.example.onelinetoend.View.UtilView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.EditText;
import android.widget.NumberPicker;


import com.example.onelinetoend.R;

import androidx.core.content.ContextCompat;

public class MyNumPicker extends NumberPicker {
    public MyNumPicker(Context context) {
        super(context);
    }

    public MyNumPicker(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyNumPicker(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void addView(View child) {
        super.addView(child);
        updateView(child);
    }

    @Override
    public void addView(View child, int index,
                        android.view.ViewGroup.LayoutParams params) {
        super.addView(child, index, params);
        updateView(child);
    }

    @Override
    public void addView(View child, android.view.ViewGroup.LayoutParams params) {
        super.addView(child, params);
        updateView(child);
    }

    private void updateView(View view) {
        if (view instanceof EditText) {
            //这里修改显示字体的属性，主要修改颜色
            ((EditText) view).setTextColor(ContextCompat.getColor(getContext(), R.color.colorGray));
            ((EditText) view).setTextSize(30);
        }
    }
}
