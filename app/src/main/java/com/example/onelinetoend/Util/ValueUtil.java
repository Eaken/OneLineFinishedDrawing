package com.example.onelinetoend.Util;

import android.content.Context;
import android.content.res.Resources;
import android.view.ViewGroup;

import com.example.onelinetoend.Model.Bean.Bean_Road;
import com.example.onelinetoend.View.Fragment.DifficultyDetailFragment;
import com.example.onelinetoend.View.Fragment.DifficultyFragment;
import com.example.onelinetoend.View.Fragment.IndexFragment;
import com.example.onelinetoend.View.Fragment.RandomRoadFragment;
import com.example.onelinetoend.View.Fragment.RoadFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

import androidx.annotation.NonNull;

public class ValueUtil {

    private final static Random random=new Random();

    private static Comparator<Integer> integerComparator;

    public final static String debugerTag = "OneLineToEnd_Debug";
    public final static String appName = "OneLineToEnd";

    public final static int Default_Unused_Code = -444;

    public static boolean toPlayMusic = true;
    public final static String key_toPlayMusic = "key_toPlayMusic";

    private static FindRoadUtil sqlFindRoadUtil;
    public static boolean toFindRoadToSql = true;
    public final static String key_toFindRoadToSql = "key_toFindRoadToSql";


    public static FindRoadUtil queueFindRoadUtil;
    public final static Queue<Bean_Road> roadQueue=new ConcurrentLinkedQueue<>();

    public final static String Fragment_Reqest_Code = "Fragment_Reqest_Code";
    public final static String Cur_Detail_Position = "Cur_Detail_Position";
    public final static String Cur_Road_Position = "Cur_Road_Position";

    public static final int Fragment_LaunchModel_Standard = 0;
    public static final int Fragment_LaunchModel_SingleTask = 1;
    public static final int Fragment_LaunchModel_SingleTop = 2;
    public static final Map<Class,Integer> fragmentLaunchModeMap = new ConcurrentHashMap<>();

    public final static long animateTime = 300L;
    public final static String anim_translationY = "translationY";
    public final static String anim_translationX = "translationX ";
    public final static String anim_rotationZ = "rotation";
    public final static String anim_rotationX = "rotationX";
    public final static String anim_rotationY = "rotationY";
    public final static String anim_scaleX = "scaleX";
    public final static String anim_scaleY = "scaleY";
    public final static String anim_alpha = "alpha";

    public static final ViewGroup.LayoutParams VLP_M_W = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
    public static final ViewGroup.LayoutParams VLP_W_M = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
    public static final ViewGroup.LayoutParams VLP_M_M = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
    public static final ViewGroup.LayoutParams VLP_W_W = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);


    static {
        fragmentLaunchModeMap.put(RandomRoadFragment.class,Fragment_LaunchModel_SingleTask);
        fragmentLaunchModeMap.put(IndexFragment.class,Fragment_LaunchModel_SingleTask);
        fragmentLaunchModeMap.put(DifficultyFragment.class,Fragment_LaunchModel_SingleTask);
        fragmentLaunchModeMap.put(DifficultyDetailFragment.class,Fragment_LaunchModel_SingleTask);
        fragmentLaunchModeMap.put(RoadFragment.class,Fragment_LaunchModel_SingleTask);
    }

    public static int getFragmentLaunchMode(Class clz){
        Integer r = fragmentLaunchModeMap.get(clz);
        return (r==null || (r!=Fragment_LaunchModel_Standard && r!=Fragment_LaunchModel_SingleTask && r!=Fragment_LaunchModel_SingleTop))?
                Fragment_LaunchModel_Standard:r;
    }

    //为指定行列数和障碍数寻路并放入队列中
    public static void findRoadToQueue(@NonNull Context context, final int rows, final int columns, final int difficulties, final boolean passPassed) {
        queueFindRoadUtil=createFindRoadUtil(context,queueFindRoadUtil);
        queueFindRoadUtil.rows=rows;
        queueFindRoadUtil.columns=columns;
        queueFindRoadUtil.difficulties=difficulties;
        roadQueue.clear();
        ThreadUtil.getInstance().addRunableToSingleThead("getRoadToQueue",() -> {
            while (true){
                try {
                    Thread.sleep(10);
                }catch (Exception e){
                    OtherUtil.DebuggerLog(e);
                }
                boolean isNew = false;
                queueFindRoadUtil=createFindRoadUtil(context,queueFindRoadUtil);
                for(int sp =0;sp<rows*columns;sp++){
                    final List<Integer> pList = new ArrayList<>();
                    for(int i=0;i<rows*columns;i++){
                        if(i!=sp) pList.add(i);
                    }

                    Collections.shuffle(pList);
                    final int sp1 = sp;
                    final List<List<Integer>> comForbiddensList = ValueUtil.comNumLists(null, 100, pList.toArray(new Integer[0]), difficulties, new checkBooleanInterface<String>() {
                        @Override
                        public boolean checkBoolean(String obj) {
                            if(queueFindRoadUtil==null) return true;
                            return !queueFindRoadUtil.mySql.checkErrorYibi(rows,columns,obj,sp1);
                        }
                    });
                    queueFindRoadUtil.startToFindRoad=true;
                    for (List<Integer> forbiddenPositionList : comForbiddensList){
                        if(!queueFindRoadUtil.startToFindRoad) break;
                        if(queueFindRoadUtil.mySql.checkErrorYibi(rows,columns,ValueUtil.getListString(forbiddenPositionList),sp)) continue;
                        Bean_Road road = queueFindRoadUtil.getARoad(rows,columns,sp,forbiddenPositionList,passPassed);

                        if(queueFindRoadUtil.rows!=rows
                                || queueFindRoadUtil.columns!=columns
                                || queueFindRoadUtil.difficulties!=difficulties){
                            roadQueue.clear();
                            isNew = true;
                            break;
                        }else if(roadQueue.size()<200&&road!=null&&!checkRoadInQueue(road)){
                            roadQueue.offer(road);
                        }
                    }
                    if(isNew) break;
                }
                if(isNew) break;
            }
        });
    }

    private static boolean checkRoadInQueue(Bean_Road road){
        if(road==null) return true;
        for(Bean_Road r : roadQueue){
            if(r==null) continue;
            if(r.getRoadString().equals(road.getRoadString())){
                return true;
            }
        }
        return false;
    }

    private static FindRoadUtil createFindRoadUtil(Context context,FindRoadUtil findRoadUtil){
        if(findRoadUtil ==null || !ViewUtil.isContextExisted(findRoadUtil.getContext()))
            findRoadUtil = new FindRoadUtil(context);
        return findRoadUtil;
    }

    //这个方法是用来保证时刻寻路并把路径放入数据库
    public static void findRanRoadToSql(Context context){
        toFindRoadToSql = true;
        ThreadUtil.getInstance().addRunableToSingleThead("findRoadToSql",() -> {
            OtherUtil.DebuggerLog("开始findtosql");
            while (toFindRoadToSql){
                try {
                    Thread.sleep(10);
                }catch (Exception e){
                    OtherUtil.DebuggerLog(e);
                }
                sqlFindRoadUtil = createFindRoadUtil(context,sqlFindRoadUtil);
                int rows=random.nextInt(8)+3;
                int columns=random.nextInt(4)+3;
                int difficulties=random.nextInt(rows*columns/2- Math.min(rows,columns))+ Math.min(rows,columns)-1;

                for(int sp=0;sp<rows*columns;sp++){
                    final List<Integer> pList = new ArrayList<>();
                    for(int i=0;i<rows*columns;i++){
                        if(i!=sp) pList.add(i);
                    }
                    Collections.shuffle(pList);
                    final int sp1 = sp;
                    final List<List<Integer>> comForbiddensList = ValueUtil.comNumLists(null, 10, pList.toArray(new Integer[0]), difficulties, new checkBooleanInterface<String>() {
                        @Override
                        public boolean checkBoolean(String obj) {
                            if(sqlFindRoadUtil==null) return true;
                            return !sqlFindRoadUtil.mySql.checkErrorYibi(rows,columns,obj,sp1);
                        }
                    });
                    sqlFindRoadUtil.startToFindRoad=true;
                    for (List<Integer> forbiddenPositionList : comForbiddensList){
                        if(!sqlFindRoadUtil.startToFindRoad) break;
                        if(sqlFindRoadUtil.mySql.checkErrorYibi(rows,columns,ValueUtil.getListString(forbiddenPositionList),sp)) continue;

                        sqlFindRoadUtil.findingRoad = true;
                        sqlFindRoadUtil.findRoads(rows,columns,sp,null,forbiddenPositionList,null,null);
                    }
                }
            }
            OtherUtil.DebuggerLog("停止findtosql");
        });
    }

    public static int DpToPx(float dp) {
        final float scale = Resources.getSystem().getDisplayMetrics().density; //当前屏幕密度因子
        return (int) (dp * scale + 0.5f);
    }

    public static int PxToDp(float px) {
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        return (int) (px / scale + 0.5f);
    }

    public static int PxToSp(float px) {
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int) (px / fontScale + 0.5f);
    }

    public static int DpToSp(float dp){
        final float scale = Resources.getSystem().getDisplayMetrics().density;
        final float fontScale = Resources.getSystem().getDisplayMetrics().scaledDensity;
        return (int)(dp*scale/fontScale + 0.5f);
    }

    public static boolean isNotEmpty(CharSequence... str){
        if(str==null) return false;
        for (CharSequence s : str){
            if(s == null || s.toString().trim().length()==0){
                return false;
            }
        }
        return str.length!=0;
    }

    public static boolean isEmpty(CharSequence... str){
        if(str==null) return true;
        for (CharSequence s : str){
            if(s != null && s.toString().trim().length()!=0){
                return false;
            }
        }
        return true;
    }

    public static boolean isListEmpty(List... lists){
        if(lists==null) return true;
        for (List l : lists){
            if(l != null && l.size()!=0){
                return false;
            }
        }
        return true;
    }

    public static boolean isListNotEmpty(List... lists){
        if(lists==null) return false;
        for (List l : lists){
            if(l == null || l.size()==0){
                return false;
            }
        }
        return lists.length!=0;
    }

    public static boolean isMapEmpty(Map... maps){
        if(maps==null) return true;
        for (Map l : maps){
            if(l != null && l.size()!=0){
                return false;
            }
        }
        return true;
    }

    public static boolean isMapNotEmpty(Map... maps){
        if(maps==null) return false;
        for (Map l : maps){
            if(l == null || l.size()==0){
                return false;
            }
        }
        return maps.length!=0;
    }

    public static int findMax(int[] ints) {
        int max = ints[0];
        for (int value : ints) {
            if (value > max) {
                max = value;
            }
        }
        return max;
    }

    public static int findMin(int[] ints) {
        int min = ints[0];
        for (int value : ints) {
            if (value < min) {
                min = value;
            }
        }
        return min;
    }

    public static boolean isSimilarStrings(String str1, String str2){
        if(isEmpty(str1) || isEmpty(str2)){
            return true;
        }
        return str1.toUpperCase().contains(str2.toUpperCase()) || str2.toUpperCase().contains(str1.toUpperCase());
    }

    //返回第no个匹配到的target的位置
    public static int indexOf(String source, String target, int no){
        final int sourceLength = source.length();
        final int targetLength = target.length();

        if (targetLength == 0) {
            return 0;
        }

        if(no <= 0){
            return -1;
        }

        int matchSum = 0;

        char first = target.charAt(0);
        int max = (sourceLength - targetLength);

        for (int i = 0; i <= max; i++) {
            /* Look for first character. */
            if (source.charAt(i)!= first) {
                while (++i <= max && source.charAt(i) != first);
            }

            /* Found first character, now look at the rest of v2 */
            if (i <= max) {
                int j = i + 1;
                int end = j + targetLength - 1;
                for (int k = 1; j < end && source.charAt(j)
                        == target.charAt(k); j++, k++);

                if (j == end) {
                    /* Found whole string. */
                    matchSum++;
                    if(matchSum==no) return i;
                }
            }
        }
        return -1;
    }

    public static long getNumberQueueSum(int i){
        if(i<=0) return 0;
        long sum = 1L;
        while (i>0){
            sum = i*sum;
            i--;
        }
        return sum;
    }

    public static long getNumberComSum(int down, int up){
        if(down<up) return 0;
        if(down==up) return 1;
        final long o1=getNumberQueueSum(down),o2=getNumberQueueSum(up),o3=getNumberQueueSum(down-up);
        if(o2*o3==0) return 0;
        return o1/(o2*o3) ;
    }

    //求排列
    public static List<List<Integer>> queueNumList( List<List<Integer>> sums,int maxSize,int startSum, Integer[] queueNums,  boolean[] signs, Integer[] nums, int maxSelect,int i,int opAddSum){
        nums = removeRepeatNum(nums);

        if(maxSelect<=0) maxSelect=Math.min(1, nums.length);
        if(maxSelect>nums.length) maxSelect=nums.length;
        if(i<0) i=0;
        if(i>=maxSelect) i=maxSelect-1;
        if(sums == null) sums = new ArrayList<>();
        if(nums.length<=0) return sums;
        if(signs==null || signs.length!=nums.length) signs = new boolean[nums.length];
        if(queueNums==null || queueNums.length!=maxSelect) queueNums = new Integer[maxSelect];

        final int resultSize = (int) (getNumberQueueSum(nums.length)/getNumberQueueSum(maxSelect));
        if(startSum+maxSize>resultSize) startSum = resultSize-maxSize;
        if(startSum<0) startSum=0;
        if(opAddSum<0) opAddSum=0;

        for(int a=0;a<nums.length;a++){
            if(!signs[a]){
                queueNums[i]=nums[a];
                signs[a]=true;
                if(i==maxSelect-1){
                    opAddSum++;
                    if(opAddSum>=startSum)
                        sums.add(Arrays.asList(queueNums.clone()));
                    if(sums.size()>maxSize && sums.size()>0) sums.remove(sums.size()-1);
                }else {
                    queueNumList(sums,maxSize,startSum,queueNums,signs,nums,maxSelect,i+1,opAddSum);
                }
                signs[a]=false;
            }
        }
        return sums;
    }

    //求组合
    public static List<List<Integer>> comNumLists(List<List<Integer>> result,int maxSize, Integer[] nums, int maxSelect,checkBooleanInterface<String> booleanInterface) {
        nums = removeRepeatNum(nums);

        if(result==null) result = new ArrayList<>();
        int n = nums.length;
        if (maxSelect > n) {
            maxSelect = n;
        }
        if(maxSelect<=0) maxSelect=Math.min(1, n);

        final int resultSize = (int) getNumberComSum(nums.length,maxSelect);
        int startSum = resultSize>0?random.nextInt(resultSize):0;
        if(startSum+maxSize>resultSize) startSum = resultSize-maxSize;
        if(startSum<0) startSum=0;


        //标识
        Integer[] bs = new Integer[n];
        for (int i = 0; i < n; i++) {
            bs[i] = 0;
        }
        // 初始化
        for (int i = 0; i < maxSelect; i++) {
            bs[i] = 1;
        }
        boolean flag = nums.length>0;

        int opAddSum = 0;
        while (flag) {
            opAddSum++;
            if(result.size()>=maxSize) return result;
            boolean tempFlag = true;
            int pos = 0;
            int sum = 0;
            if(opAddSum>=startSum){
                final List<Integer> r = getComNumList(bs, nums, maxSelect);
                sortIntegerList(r);
                if(booleanInterface!=null){
                    if(booleanInterface.checkBoolean(ValueUtil.getListString(r))){
                        result.add(r);
                    }
                }else {
                    result.add(r);
                }
            }

            // 首先找到第一个10组合，然后变成01
            for (int i = 0; i < n - 1; i++) {
                if (bs[i] == 1 && bs[i + 1] == 0) {
                    bs[i] = 0;
                    bs[i + 1] = 1;
                    pos = i;
                    break;
                }
            }

            // 将左边的1全部移动到数组的最左边
            for (int i = 0; i < pos; i++) {
                if (bs[i] == 1) {
                    sum++;
                }
            }
            for (int i = 0; i < pos; i++) {
                if (i < sum) {
                    bs[i] = 1;
                } else {
                    bs[i] = 0;
                }
            }

            // 检查是否所有的1都移动到了最右边
            for (int i = n - maxSelect; i < n; i++) {
                if (bs[i] == 0) {
                    tempFlag = false;
                    break;
                }
            }
            if (!tempFlag) {
                flag = true;
            } else {
                flag = false;
                if(maxSelect!=n) {//两者相等时上面的位移操作不会有效，即数字没变，所以不添加
                    final List<Integer> r = getComNumList(bs, nums, maxSelect);
                    sortIntegerList(r);
                    if(booleanInterface!=null){
                        if(booleanInterface.checkBoolean(ValueUtil.getListString(r))){
                            result.add(r);
                        }
                    }else {
                        result.add(r);
                    }
                }
            }

        }
        return result;
    }

    private static List<Integer> getComNumList(Integer[] bs, Integer[] a, int m) {
        Integer[] result = new Integer[m];
        int pos = 0;
        for (int i=0;i<bs.length && pos<m;i++) {
            if (bs[i] == 1) {
                result[pos] = a[i];
                pos++;
            }
        }
        return Arrays.asList(result);
    }

    public static Integer[] removeRepeatNum(Integer... ints) {
        if(ints==null) return new Integer[0];
        if(ints.length==0) return ints;
        List<Integer> list = new ArrayList<>();
        for (Integer anInt : ints) {
            if (!list.contains(anInt)) {
                list.add(anInt);
            }
        }
        return list.toArray(new Integer[0]);
    }

    public static String getArrayString(Object[] array){
        StringBuilder roadString=new StringBuilder();
        if(array!=null){
            for(int p=0;p<array.length;p++){
                roadString.append(array[p]);
                if(p!=array.length-1){
                    roadString.append(",");
                }
            }
        }
        return roadString.toString();
    }

    public static String getListString(List list){
        StringBuilder roadString=new StringBuilder();
        if(list!=null){
            for(int p=0;p<list.size();p++){
                roadString.append(list.get(p));
                if(p!=list.size()-1){
                    roadString.append(",");
                }
            }
        }
        return roadString.toString();
    }

    public static List<Integer> getIntListFromStrs(String[] strings){
        final List<Integer> list = new ArrayList<>();
        for(String p:strings){
            try {
                list.add(Integer.parseInt(p));
            }catch (Exception e){
                list.clear();
                break;
            }
        }
        return list;
    }

    public static void sortIntegerList(List<Integer> list){
        if(isListEmpty(list)) return;
        if(integerComparator==null){
            synchronized (ValueUtil.class){
                integerComparator = (o1, o2) -> {
                    if(o1==null && o2==null) return 0;
                    if(o1==null) return -1;
                    if(o2==null) return 1;
                    return o1.compareTo(o2);
                };
            }
        }
        Collections.sort(list,integerComparator);
    }

    public interface checkBooleanInterface<t>{
        boolean checkBoolean(t obj);
    }
}
