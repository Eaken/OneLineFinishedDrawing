package com.example.onelinetoend.View.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.onelinetoend.Model.Bean.Bean_Road;
import com.example.onelinetoend.R;
import com.example.onelinetoend.Util.OtherUtil;
import com.example.onelinetoend.Util.RoadValuesUtil;
import com.example.onelinetoend.Util.ValueUtil;
import com.example.onelinetoend.Util.ViewUtil;
import com.example.onelinetoend.View.Activity.MainActivity;
import com.example.onelinetoend.View.UtilView.Grid_Yibi;

import java.util.List;

import butterknife.BindView;

public class RoadFragment extends BaseFragment implements Grid_Yibi.yibiListener {

    @BindView(R.id.roadHint)
    TextView roadHint;

    @BindView(R.id.grid_yibi)
    Grid_Yibi grid_yibi;

    @BindView(R.id.nextButton)
    ImageButton nextButton;

    @BindView(R.id.refreshButton)
    ImageButton refreshButton;

    @BindView(R.id.returnButton)
    ImageButton returnButton;

    @BindView(R.id.helpButton)
    ImageButton helpButton;

    @BindView(R.id.homeButton)
    ImageButton homeButton;

    private boolean firstPassed = false;
    private boolean ishelping=false;
    private int curDetailPosition = -1;
    private int curRoadPosition = -1;

    @Override
    public int getLayoutId() {
        return R.layout.layout_road;
    }

    @Override
    public void onNewArguments() {
        super.onNewArguments();
        isLoaded = initView();
    }

    @Override
    public boolean initView() {
        firstPassed = false;
        ishelping=false;
        returnButton.setOnClickListener(v->onBackClick());
        refreshButton.setOnClickListener(v->runOnUiThread(()->grid_yibi.refreshGrid()));
        helpButton.setOnClickListener(v->{
            if(!ishelping){
                grid_yibi.getHelp();
            }else {
                runOnUiThread(()->grid_yibi.refreshGrid());
            }
        });
        homeButton.setOnClickListener(v -> runOnUiThread(()->{
            if(getActivity() instanceof MainActivity){
                ((MainActivity)getActivity()).startFragment(IndexFragment.class,0,null,null);
            }else {
                showToast("跳转失败，请联系开发人员");
            }
        }));
        nextButton.setOnClickListener(v -> goNext());

        curDetailPosition = getNonNullArguments().getInt(ValueUtil.Cur_Detail_Position,-1);
        curRoadPosition = getNonNullArguments().getInt(ValueUtil.Cur_Road_Position,-1);

        if(getCurRoad()!=null){
            grid_yibi.initGrid(getCurRoad(),this);
        }else {
            grid_yibi.refreshGrid();
        }
        roadHint.setText((curDetailPosition+1)+"-"+(curRoadPosition+1));
        setResult(0,new Intent(){{putExtras(getNonNullArguments());}});
        return true;
    }

    private void goNext(){
        if(checkPosition()){
            int cdp = curDetailPosition;
            int crp = curRoadPosition+1;
            if(crp>=RoadValuesUtil.roadValuesList.get(cdp).size()){
                crp=0;
                cdp++;
            }
            if(cdp>=RoadValuesUtil.roadValuesList.size()){
                showToast("当前已是最后一关!");
                return;
            }
            if(getActivity() instanceof MainActivity){
                Bundle bundle = getNonNullArguments();
                bundle.putInt(ValueUtil.Cur_Detail_Position,cdp);
                bundle.putInt(ValueUtil.Cur_Road_Position,crp);
                ((MainActivity)getActivity()).startFragment(RoadFragment.class,0,bundle,null);
            }else {
                showToast("跳转失败，请联系开发人员");
            }
        }
    }

    private boolean checkPosition(){
        if(curDetailPosition>=0
                && curRoadPosition>=0
                && curDetailPosition<RoadValuesUtil.roadValuesList.size()
                && curRoadPosition<RoadValuesUtil.roadValuesList.get(curDetailPosition).size()) return true;
        showToast("获取地图失败!,关卡:"+(curDetailPosition+1)+",地图:"+(curRoadPosition+1));
        return false;
    }

    private Bean_Road getCurRoad(){
        if(checkPosition())
            return RoadValuesUtil.roadValuesList.get(curDetailPosition).get(curRoadPosition);
        return null;
    }

    @Override
    public void initGirdRoad(int initRows, int initColums, int initDifficulties) {

    }

    @Override
    public boolean stopGettingRoad() {
        return true;
    }

    @Override
    public void saveYibi(Bean_Road road, List<Integer> passedPositions) {

    }

    @Override
    public void passed(Bean_Road road) {
        if(road==null) return;
        if(!firstPassed){
            firstPassed=true;
            getMySql().insertPassedYibi(road);
            ViewUtil.getAskDialog( "","恭喜通过",new OtherUtil.OnCallBackListenerImpl<Boolean>(){
                @Override
                public void OnCallBackFirst(Boolean... params) {
                    goNext();
                }
            },"下一关", "算了");
        }
    }

    @Override
    public void setIsHelping(boolean isHelping) {
        runOnUiThread(()->{
            if(isHelping){
                helpButton.setBackgroundResource(R.drawable.ic_helping);
            }else {
                helpButton.setBackgroundResource(R.drawable.ic_help);
            }
            this.ishelping=isHelping;
        });
    }

    @Override
    public boolean isHelping() {
        return ishelping;
    }
}
