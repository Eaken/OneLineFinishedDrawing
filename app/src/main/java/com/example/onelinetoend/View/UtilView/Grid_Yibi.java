package com.example.onelinetoend.View.UtilView;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.Toast;

import com.example.onelinetoend.Adapter.Adapter_Yibi;
import com.example.onelinetoend.Model.Bean.Bean_Road;
import com.example.onelinetoend.R;
import com.example.onelinetoend.Util.OtherUtil;
import com.example.onelinetoend.Util.ValueUtil;

import java.util.ArrayList;
import java.util.List;

public class Grid_Yibi extends GridView implements View.OnTouchListener {

    public interface yibiListener{
        void initGirdRoad(final int initRows, final int initColums, final int initDifficulties);
        boolean stopGettingRoad();
        void saveYibi(Bean_Road road, List<Integer> passedPositions);
        void passed(Bean_Road road);
        void setIsHelping(boolean isHelping);
        boolean isHelping();
    }

    private int firstChildPosition=-1;//初始位置
    private int lastChildPosition=-1;//记录上次有变更的子view的位置
    private int downChildPosition=-1;//记录每次的落点
    private int lastMoveChildPosition=-1;//记录当前滑动周期内最后一个被滑过的子view的位置

    Bean_Road road; //当前可以通关的路
    private final List<Integer> passedPositions = new ArrayList<>();//当前走过的路径,不包含起始位置

    private yibiListener listener;

    public void refreshGrid(){
        if(road==null) return;
        initGrid(road,listener);
    }

    public void initGrid(Bean_Road road,yibiListener listener){
        if(listener!=null)
            listener.setIsHelping(false);
        if(road==null){
            showToat("获取的地图错误！");
            return;
        }
        this.road=road;
        this.passedPositions.clear();
        this.listener=listener;
        this.lastChildPosition=road.getRoadList().get(0);
        this.firstChildPosition=road.getRoadList().get(0);
        if(listener!=null)
            listener.stopGettingRoad();
        setNumColumns(road.getColumns());
        setWidth();
        setAdapter(new Adapter_Yibi(road));
    }

    private void showToat(String mes){
        post(()->Toast.makeText(getContext(),mes, Toast.LENGTH_SHORT).show());
    }

    public void initPassedGrid(final int rows, final int columns, final int difficulties, final String roadposition, final String passedPosition,final yibiListener listener){
        if(listener!=null)
            listener.setIsHelping(false);
        String[] roadpositions=roadposition.split("[,]");
        final List<Integer> roadList= ValueUtil.getIntListFromStrs(roadpositions);
        if(ValueUtil.isListEmpty(roadList)){
            showToat("恢复上次游戏失败，正在重开一局。。。");
            if(listener!=null)
                listener.initGirdRoad(rows,columns,difficulties);
            return;
        }
        this.road= new Bean_Road(rows,columns,roadList);
        this.passedPositions.clear();
        this.listener=listener;
        this.lastChildPosition=roadList.get(0);
        this.firstChildPosition=roadList.get(0);
        setNumColumns(columns);
        setWidth();
        setAdapter(new Adapter_Yibi(road));

        if(passedPosition.length()==0) {
            if(listener!=null)
                listener.stopGettingRoad();
            return;
        }

        post(() -> {
            if(listener!=null)
                listener.stopGettingRoad();
            String[] passedPositions=passedPosition.split("[,]");
            for(String passP:passedPositions){
                try {
                    int p= Integer.parseInt(passP);
                    View childView=getChildAt(p);
                    QianJin(childView,p);
                }catch (Exception e){
                    OtherUtil.DebuggerLog(e);
                    if(listener!=null)
                        listener.initGirdRoad(rows,columns,difficulties);
                    showToat("恢复上次游戏失败，正在重开一局。。。");
                    return;
                }
            }
            if(passedPositions.length+1==roadList.size()){
                if(listener!=null)
                    listener.passed(road);
            }
        });
    }

    private void setWidth(){
        ViewGroup.LayoutParams params=getLayoutParams();
        params.width=ValueUtil.DpToPx(60)*(road==null?0:road.getColumns());
        setLayoutParams(params);
    }

    public Grid_Yibi(Context context) {
        super(context);
        setOnTouchListener(this);
    }

    public Grid_Yibi(Context context, AttributeSet attrs) {
        super(context, attrs);
        setOnTouchListener(this);
    }

    public Grid_Yibi(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setOnTouchListener(this);
    }

    private void QianJin(View curChildview, int currentChildPosition){
        if(curChildview==null) return;
        post(()->{
            if(lastChildPosition+1==currentChildPosition&&currentChildPosition%getNumColumns()!=0
                    || lastChildPosition+getNumColumns()==currentChildPosition
                    || lastChildPosition-getNumColumns()==currentChildPosition
                    ||lastChildPosition-1==currentChildPosition&&lastChildPosition%getNumColumns()!=0){

                if(lastChildPosition<getChildCount()&&lastChildPosition>=0){
                    View lastchildview=getChildAt(lastChildPosition);
                    View nowbase=curChildview.findViewById(R.id.baseyibi);
                    if(lastChildPosition+1==currentChildPosition&&currentChildPosition%getNumColumns()!=0){
                        //当当前位置处于上个被选中的位置的右边时，选中当前位置。其它前进情况和后退情况类似
                        View lastright=lastchildview.findViewById(R.id.rightyibi)
                                ,nowleft=curChildview.findViewById(R.id.leftyibi);
                        lastright.setBackgroundResource(R.color.colorGray);
                        nowleft.setBackgroundResource(R.color.colorGray);
                        nowbase.setBackgroundResource(R.drawable.shape_gray_selected);
                    }else if(lastChildPosition+getNumColumns()==currentChildPosition){
                        View lastbottom=lastchildview.findViewById(R.id.bottomyibi)
                                ,nowtop=curChildview.findViewById(R.id.topyibi);
                        lastbottom.setBackgroundResource(R.color.colorGray);
                        nowtop.setBackgroundResource(R.color.colorGray);
                        nowbase.setBackgroundResource(R.drawable.shape_gray_selected);
                    }else if(lastChildPosition-getNumColumns()==currentChildPosition){
                        View lasttop=lastchildview.findViewById(R.id.topyibi)
                                ,nowbottom=curChildview.findViewById(R.id.bottomyibi);
                        lasttop.setBackgroundResource(R.color.colorGray);
                        nowbottom.setBackgroundResource(R.color.colorGray);
                        nowbase.setBackgroundResource(R.drawable.shape_gray_selected);
                    }else if(lastChildPosition-1==currentChildPosition&&lastChildPosition%getNumColumns()!=0){
                        View lastleft=lastchildview.findViewById(R.id.leftyibi)
                                ,nowright=curChildview.findViewById(R.id.rightyibi);
                        lastleft.setBackgroundResource(R.color.colorGray);
                        nowright.setBackgroundResource(R.color.colorGray);
                        nowbase.setBackgroundResource(R.drawable.shape_gray_selected);
                    }
                    passedPositions.add(currentChildPosition);
                    if(listener!=null)
                        listener.saveYibi(road,passedPositions);
                    curChildview.setTag(lastChildPosition);
                    lastChildPosition=currentChildPosition;
                }
            }
        });
    }

    //代码太多又简单所以我直接复制前进方法过来改
    public void getHelp(){
        refreshGrid();
        post(() -> {
            if(road==null) return;
            if(listener!=null)
                listener.setIsHelping(true);
            int lastChildPosition=road.getRoadList().get(0);
            for(int i = 1; i<road.getRoadList().size(); i++){
                int currentChildPosition=road.getRoadList().get(i);
                View childview=getChildAt(currentChildPosition);
                if(childview==null) continue;
                if(lastChildPosition+1==currentChildPosition&&currentChildPosition%getNumColumns()!=0
                        || lastChildPosition+getNumColumns()==currentChildPosition
                        || lastChildPosition-getNumColumns()==currentChildPosition
                        ||lastChildPosition-1==currentChildPosition&&lastChildPosition%getNumColumns()!=0){

                    if(lastChildPosition<getChildCount()&&lastChildPosition>=0){
                        View lastchildview=getChildAt(lastChildPosition);
                        View nowbase=childview.findViewById(R.id.baseyibi);
                        if(lastChildPosition+1==currentChildPosition&&currentChildPosition%getNumColumns()!=0){
                            //当当前位置处于上个被选中的位置的右边时，选中当前位置。其它前进情况和后退情况类似
                            View lastright=lastchildview.findViewById(R.id.rightyibi)
                                    ,nowleft=childview.findViewById(R.id.leftyibi);
                            lastright.setBackgroundResource(R.color.colorGray_tran);
                            nowleft.setBackgroundResource(R.color.colorGray_tran);
                            nowbase.setBackgroundResource(R.drawable.shape_gray_tran_selected);
                        }else if(lastChildPosition+getNumColumns()==currentChildPosition){
                            View lastbottom=lastchildview.findViewById(R.id.bottomyibi)
                                    ,nowtop=childview.findViewById(R.id.topyibi);
                            lastbottom.setBackgroundResource(R.color.colorGray_tran);
                            nowtop.setBackgroundResource(R.color.colorGray_tran);
                            nowbase.setBackgroundResource(R.drawable.shape_gray_tran_selected);
                        }else if(lastChildPosition-getNumColumns()==currentChildPosition){
                            View lasttop=lastchildview.findViewById(R.id.topyibi)
                                    ,nowbottom=childview.findViewById(R.id.bottomyibi);
                            lasttop.setBackgroundResource(R.color.colorGray_tran);
                            nowbottom.setBackgroundResource(R.color.colorGray_tran);
                            nowbase.setBackgroundResource(R.drawable.shape_gray_tran_selected);
                        }else if(lastChildPosition-1==currentChildPosition&&lastChildPosition%getNumColumns()!=0){
                            View lastleft=lastchildview.findViewById(R.id.leftyibi)
                                    ,nowright=childview.findViewById(R.id.rightyibi);
                            lastleft.setBackgroundResource(R.color.colorGray_tran);
                            nowright.setBackgroundResource(R.color.colorGray_tran);
                            nowbase.setBackgroundResource(R.drawable.shape_gray_tran_selected);
                        }
                        lastChildPosition=currentChildPosition;
                    }
                }
            }
        });
    }

    //后退操作需谨慎，因为前进时后退或者直接后退不一样，需分别处理，不像前进只需要对比上一步位置和当前所在位置即可判断是否前进
    private void HouTui(View curChildview, int currentChildPosition){
        if(curChildview==null) return;
        post(()->{
            if(!(curChildview.getTag() instanceof Integer)) return;
            if(lastChildPosition<getChildCount()&&lastChildPosition>=0){
                lastChildPosition=(Integer)curChildview.getTag();
                View lastchildview=getChildAt(lastChildPosition);
                if(lastChildPosition+1==currentChildPosition&&currentChildPosition%getNumColumns()!=0){//从右侧往左后退
                    View lastright=lastchildview.findViewById(R.id.rightyibi);
                    lastright.setBackgroundResource(R.color.colortransparency);
                }else if(lastChildPosition+getNumColumns()==currentChildPosition){//从下方往上后退
                    View lastbottom=lastchildview.findViewById(R.id.bottomyibi);
                    lastbottom.setBackgroundResource(R.color.colortransparency);
                }else if(lastChildPosition-getNumColumns()==currentChildPosition){//从上方往下后退
                    View lasttop=lastchildview.findViewById(R.id.topyibi);
                    lasttop.setBackgroundResource(R.color.colortransparency);
                }else if(lastChildPosition-1==currentChildPosition&&lastChildPosition%getNumColumns()!=0){//从左侧往右后退
                    View lastleft=lastchildview.findViewById(R.id.leftyibi);
                    lastleft.setBackgroundResource(R.color.colortransparency);
                }
                passedPositions.remove(passedPositions.size()-1);
                if(listener!=null)
                    listener.saveYibi(road,passedPositions);
                curChildview.setTag(null);
                View right=curChildview.findViewById(R.id.rightyibi)
                        ,left=curChildview.findViewById(R.id.leftyibi)
                        ,top=curChildview.findViewById(R.id.topyibi)
                        ,bottom=curChildview.findViewById(R.id.bottomyibi)
                        ,nowbase=curChildview.findViewById(R.id.baseyibi);
                right.setBackgroundResource(R.color.colortransparency);
                left.setBackgroundResource(R.color.colortransparency);
                top.setBackgroundResource(R.color.colortransparency);
                bottom.setBackgroundResource(R.color.colortransparency);
                nowbase.setBackgroundResource(R.drawable.shape_gray_unselected);
            }
        });
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        if(listener!=null && listener.isHelping()){
            refreshGrid();
            listener.setIsHelping(false);
        }
        if(road==null) return false;

        final List<Integer> roadList = road.getRoadList();
        if(listener!=null&&passedPositions.size()+1==roadList.size()){
            StringBuilder roadString=new StringBuilder();

            for(int p:roadList){
                roadString.append(p);
                if(roadList.lastIndexOf(p)!=roadList.size()-1){
                    roadString.append(",");
                }
            }
            listener.passed(road);
        }
        float currentX=event.getX();
        float currentY=event.getY();
        int currentChildPosition=pointToPosition((int)currentX,(int)currentY);
        if(currentChildPosition>=getChildCount() || currentChildPosition<0){
            return false;
        }
        final View childview=getChildAt(currentChildPosition);
        if(childview.getTag()!=null&&childview.getTag().toString().equals("forbidden")){
            return false;
        }
        try {
            switch (event.getAction()){
                case MotionEvent.ACTION_DOWN:
                    //记录落点
                    downChildPosition=currentChildPosition;

                    if(lastChildPosition==downChildPosition || downChildPosition==firstChildPosition || passedPositions.contains(downChildPosition)){
                        if(lastChildPosition==downChildPosition){
                            //当上次操作的view位置和当前所落的位置一样时执行后退
                            HouTui(childview,downChildPosition);
                        }else{
                            //当 落点在起点位置 或者 已通过的点包含当前落点时，回退到落点
                            for(int i=passedPositions.size()-1;i>=0;i--){
                                final int curP = passedPositions.get(i);
                                if(curP==downChildPosition) break;
                                View curView = getChildAt(curP);
                                if(curView.getTag()==null) break;
                                HouTui(curView,curP);
                            }
                        }
                    }else{
                        //其它情况尝试前进
                        QianJin(childview,downChildPosition);
                    }
                    break;
                case MotionEvent.ACTION_MOVE:
                    //当前位置为初始位置时需做特殊处理
                    if(currentChildPosition==firstChildPosition&&lastChildPosition!=firstChildPosition){
                        View lastChildView=getChildAt(lastChildPosition);
                        if(lastChildView.getTag() instanceof Integer &&(Integer)lastChildView.getTag()==firstChildPosition){
                            //当前位置为初始位置并且当“笔尾”的前一个位置为初始位置时后退
                            HouTui(lastChildView,lastChildPosition);
                        }
                        break;
                    }
                    if(currentChildPosition!=lastMoveChildPosition){//仅在一个子view内来回滑动时状态不改变
                        if(currentChildPosition!=downChildPosition){
                            if(childview.getTag()!=null){
                                //记录不为空时分两种情况执行后退，直接后退和前进时突然后退
                                if(lastChildPosition==currentChildPosition){
                                    //直接后退：当上次操作点等于当前移动点时执行后退
                                    HouTui(childview,currentChildPosition);
                                }else{
                                    View lastchildview=getChildAt(lastChildPosition);
                                    if(lastchildview.getTag() instanceof Integer && (Integer)lastchildview.getTag()==currentChildPosition){
                                        //前进时突然后退：退的是笔尾，此时currentChildPosition为“笔尾”的上一步位置
                                        HouTui(lastchildview,lastChildPosition);
                                    }
                                }
                            }else {
                                //当记录为空时尝试前进
                                QianJin(childview,currentChildPosition);
                            }
                        }else if(childview.getTag()==null){
                            //当当前点为落点并且记录为空时尝试前进
                            QianJin(childview,currentChildPosition);
                        }
                    }
                    lastMoveChildPosition=currentChildPosition;
                    break;
            }
        }catch (Exception e){
            OtherUtil.DebuggerLog(e);
            showToat("出错了，有一步没执行");
        }
        return false;
    }

}
