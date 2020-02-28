package com.example.onelinetoend.Util;

import android.content.Context;

import com.example.onelinetoend.Model.Bean.Bean_Road;
import com.example.onelinetoend.Model.MySql;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import androidx.annotation.Nullable;

public class FindRoadUtil {

    private Context context;
    public MySql mySql;
    private Random random = new Random();

    public FindRoadUtil(Context context){
        this.context = context;
        mySql = new MySql(context);
    }

    public Context getContext() {
        return context;
    }

    public boolean startToFindRoad=false;//用来开始寻路
    public boolean findingRoad=false;//用来判断是否获取了一张指定图的指定数量的路径
    public int rows,columns,difficulties;


    //根据行列数和难度获取一条指定路径，有可能很长时间都得不到路径，所以最好放在子线程进行
    public Bean_Road getAppointedRoad(int rows, int columns, int difficulties,boolean passPassed){
        this.rows=rows;
        this.columns=columns;
        this.difficulties=difficulties;
        startToFindRoad=true;
        Bean_Road road=null;
        while (startToFindRoad){
            final int sp =random.nextInt(rows*columns);
            List<Integer> pList = new ArrayList<>();
            for(int i=0;i<rows*columns;i++){
                if(i!=sp) pList.add(i);
            }
            Collections.shuffle(pList);
            List<List<Integer>> comForbiddensList = ValueUtil.comNumLists(null, 20, pList.toArray(new Integer[0]), difficulties, new ValueUtil.checkBooleanInterface<String>() {
                @Override
                public boolean checkBoolean(String obj) {
                    if(mySql==null) return true;
                    return !mySql.checkErrorYibi(rows,columns,obj,sp);
                }
            });
            for (List<Integer> forbiddenPositionList : comForbiddensList){
                if(!startToFindRoad) break;
                if(mySql.checkErrorYibi(rows,columns,ValueUtil.getListString(forbiddenPositionList),sp)) continue;
                road = getARoad(rows,columns,sp,forbiddenPositionList,passPassed);
            }
        }
        return road;
    }

    //根据行列数和起始点以及禁止点尝试获取一条可行路径
    public Bean_Road getARoad(int rows, int columns, int startPosition, List<Integer> forbiddenPositionList, boolean passPassed){
        List<Bean_Road> roads=new ArrayList<>();
        Bean_Road road=null;
        findingRoad=true;
        findRoads(rows,columns,startPosition,null,forbiddenPositionList,roads,null);
        if(roads.size()>0 && (road=roads.get(0))!=null){
            if(passPassed && mySql.checkPassedYibi(road)){
                return null;
            }
            startToFindRoad=false;
        }
        return road;
    }

    public void findRoads(final int row, final int column, int curPosition, @Nullable List<Integer> choosedPositions, final  List<Integer> forbiddenList,@Nullable List<Bean_Road> roads,int[] nos) {
        if(!startToFindRoad || !findingRoad) return;

        if(forbiddenList.indexOf(curPosition) != -1){
            return;
        }

        if(choosedPositions==null){
            choosedPositions = new ArrayList<>();
        }
        if(choosedPositions.isEmpty()){
            nos = new int[]{0};
            choosedPositions.add(curPosition);
        }

        if(nos==null || nos.length!=1){
            nos = new int[]{0};
        }

        //当路线完成时，退出方法，nos的数不会减
        if((choosedPositions.size()+forbiddenList.size())==row*column){
            //得到的路径只要不重复全扔到数据库，以备后用
            final List<Integer> cps = new ArrayList<>(choosedPositions);
            ThreadUtil.getInstance().runOnChildThread(() -> mySql.insertSavedYibi(new Bean_Road(row,column,cps)));

            if(roads!=null){
                roads.add(new Bean_Road(rows,columns,choosedPositions));
            }

            findingRoad=false;
            return;
        }


        //四方寻路，且路线互不影响
        if(findUpRoad(curPosition-column, choosedPositions,forbiddenList)){
            nos[0] = nos[0]+1;
            List<Integer> nextChoosedPositions = new ArrayList<>(choosedPositions);
            nextChoosedPositions.add(curPosition-column);
            findRoads(row,column,curPosition-column,nextChoosedPositions,forbiddenList,roads,nos);
        }
        if(findLeftRoad(curPosition-1,column,choosedPositions,forbiddenList)){
            nos[0] = nos[0]+1;
            List<Integer> nextChoosedPositions = new ArrayList<>(choosedPositions);
            nextChoosedPositions.add(curPosition-1);
            findRoads(row,column,curPosition-1,nextChoosedPositions,forbiddenList,roads,nos);
        }
        if(findDownRoad(curPosition+column,row*column,choosedPositions,forbiddenList)){
            nos[0] = nos[0]+1;
            List<Integer> nextChoosedPositions = new ArrayList<>(choosedPositions);
            nextChoosedPositions.add(curPosition+column);
            findRoads(row,column,curPosition+column,nextChoosedPositions,forbiddenList,roads,nos);
        }
        if(findRightRoad(curPosition+1,row*column,column,choosedPositions,forbiddenList)){
            nos[0] = nos[0]+1;
            List<Integer> nextChoosedPositions = new ArrayList<>(choosedPositions);
            nextChoosedPositions.add(curPosition+1);
            findRoads(row,column,curPosition+1,nextChoosedPositions,forbiddenList,roads,nos);
        }

        //当前路线未完成才减1
        nos[0] = nos[0]-1;

        //nos的数≤0时，即所有路线都走不通，记录错误图，一张图由起点和障碍位置确定
        if(nos[0]<=0){
            final int sp;
            if(choosedPositions.size()>0) sp = choosedPositions.get(0);
            else sp = curPosition;
            //记录错误图，减少下次寻路时间
            ThreadUtil.getInstance().runOnChildThread(() -> mySql.insertErrorYibi(row,column,ValueUtil.getListString(forbiddenList),sp));
        }

    }

    //向上寻路
    private static boolean findUpRoad(int upPosition, List<Integer> choosedPositions,  List<Integer> forbiddenCount) {
        return upPosition >= 0
                && choosedPositions.lastIndexOf(upPosition) == -1
                && forbiddenCount.indexOf(upPosition) == -1;
    }

    //向左寻路
    private static boolean findLeftRoad(int leftPosition, int column, List<Integer> choosedPositions,  List<Integer> forbiddenCount) {
        return leftPosition >= 0
                && choosedPositions.lastIndexOf(leftPosition) == -1
                && forbiddenCount.indexOf(leftPosition) == -1
                &&(leftPosition+1)%column!=0;
    }

    //向下寻路
    private static boolean findDownRoad(int downPosition, int size, List<Integer> choosedPositions,  List<Integer> forbiddenCount) {
        return downPosition < size
                && choosedPositions.lastIndexOf(downPosition) == -1
                && forbiddenCount.indexOf(downPosition) == -1;
    }

    //向右寻路
    private static boolean findRightRoad(int rightPosition, int size, int column, List<Integer> choosedPositions,  List<Integer> forbiddenCount) {
        return rightPosition <size
                && choosedPositions.lastIndexOf(rightPosition) == -1
                && forbiddenCount.indexOf(rightPosition) == -1
                &&rightPosition%column!=0;
    }

}
