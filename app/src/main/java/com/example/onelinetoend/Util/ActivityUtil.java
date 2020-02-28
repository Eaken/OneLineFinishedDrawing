package com.example.onelinetoend.Util;

import android.annotation.SuppressLint;
import android.app.Activity;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

public class ActivityUtil {

    List<Activity> list = new CopyOnWriteArrayList<>();
    @SuppressLint("StaticFieldLeak")
    private volatile static ActivityUtil ACTIVITY_UTIL;
    private Activity curActivity;

    public static ActivityUtil getInstance(){
        if(ACTIVITY_UTIL==null){
            synchronized (ActivityUtil.class){
                if (ACTIVITY_UTIL==null){
                    ACTIVITY_UTIL=new ActivityUtil();
                }
            }
        }
        return ACTIVITY_UTIL;
    }

    public Activity getCurActivity() {
        return curActivity;
    }

    public void setCurActivity(Activity curActivity) {
        this.curActivity = curActivity;
    }


    /**
     * Activity关闭时，删除Activity列表中的Activity对象*/
    public void removeActivity(Activity a){
        list.remove(a);
    }

    /**
     * 向Activity列表中添加Activity对象*/
    public void addActivity(Activity a){
        list.add(a);
    }

    public void finishActivity(){
        for (Activity activity : list) {
            if (null != activity) {
                activity.finish();
            }
        }
        //杀死该应用进程
        android.os.Process.killProcess(android.os.Process.myPid());
    }
}
