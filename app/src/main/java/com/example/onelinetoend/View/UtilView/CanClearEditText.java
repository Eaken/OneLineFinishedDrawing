package com.example.onelinetoend.View.UtilView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.inputmethod.EditorInfo;

import com.example.onelinetoend.R;

import androidx.appcompat.widget.AppCompatAutoCompleteTextView;
import androidx.core.content.ContextCompat;

public class CanClearEditText extends AppCompatAutoCompleteTextView {

    private Drawable deleceImg;
    boolean isSearchType = false;

    public boolean isSearchType() {
        return isSearchType;
    }

    public void setSearchType(boolean searchType) {
        isSearchType = searchType;
        init();
    }

    public CanClearEditText(Context context) {
        super(context);
        init();
    }

    public CanClearEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        initParams(attrs);
    }

    public CanClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initParams(attrs);
    }


    private void initParams(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CanClearEditText);
        isSearchType = typedArray.getBoolean(R.styleable.CanClearEditText_isSearchType,false);
        typedArray.recycle();
        init();
    }

    private void init(){
        setClickable(true);
        if(deleceImg==null){
            deleceImg = ContextCompat.getDrawable(getContext(), R.drawable.ic_delete);
        }
        if(isSearchType) {
            setHint("✎ 搜索");
            setImeOptions(EditorInfo.IME_ACTION_SEARCH);
            setSingleLine(true);
            setMaxLines(1);
            setThreshold(1);
        }else {
            setSingleLine(false);
            setMaxLines(Integer.MAX_VALUE);
            setHint("");
            setImeOptions(EditorInfo.IME_NULL);
        }
        if(deleceImg!=null){
            deleceImg.setColorFilter(getCurrentHintTextColor(), PorterDuff.Mode.SRC_ATOP);
        }
        addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                setDrawable();
            }
        });
        setDrawable();
    }

    //设置删除图片
    private void setDrawable() {
        setCompoundDrawablesWithIntrinsicBounds(null, null, isFocusable()&&length()>0?deleceImg:null, null);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (deleceImg != null && event.getAction() == MotionEvent.ACTION_UP) {
            boolean isClean =(event.getX() > (getWidth() - getTotalPaddingRight()))&&
                    (event.getX() < (getWidth() - getPaddingRight()));
            if(isClean) setText("");
        }
        if(onEditTextTouchListener!=null){
            onEditTextTouchListener.onEditTextTouch();
        }
        return super.onTouchEvent(event);
    }

    @SuppressWarnings({ "UnusedDeclaration" })
    @Override
    public void performFiltering(CharSequence text, int keyCode) {
        if(getFilter()==null) return;
        getFilter().filter(text, this);
    }

    @Override
    public boolean enoughToFilter() {
        return true;
    }

    @Override
    protected void onFocusChanged(boolean focused, int direction, Rect previouslyFocusedRect) {
        setDrawable();
        super.onFocusChanged(focused, direction, previouslyFocusedRect);
    }

    private onEditTextTouchListener onEditTextTouchListener;

    public void setOnEditTextTouchListener(CanClearEditText.onEditTextTouchListener onEditTextTouchListener) {
        this.onEditTextTouchListener = onEditTextTouchListener;
    }

    public interface onEditTextTouchListener{
        void onEditTextTouch();
    }
}
