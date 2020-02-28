package com.example.onelinetoend.Util;

import android.os.Debug;
import android.util.Log;

import java.io.Serializable;


public class OtherUtil {

    public static void DebuggerLog(Throwable throwable){
        throwable.printStackTrace();
        if(!Debug.isDebuggerConnected()){
            FileUtil.writeThrowleToLog(throwable);
        }
    }

    public static void DebuggerLog(String msg){
        //因为String的length是字符数量不是字节数量所以为了防止中文字符过多，
        //  把4*1024的MAX字节打印长度改为2001字符数
        int max_str_length = 2001 - ValueUtil.debugerTag.length();
        if(Debug.isDebuggerConnected()){
            //大于4000时
            while (msg.length() > max_str_length) {
                Log.e(ValueUtil.debugerTag, msg.substring(0, max_str_length));
                msg = msg.substring(max_str_length);
            }
            //剩余部分
            Log.e(ValueUtil.debugerTag, msg);
        }else {
            while (msg.length() > max_str_length) {
                System.out.println(msg.substring(0, max_str_length));
                msg = msg.substring(max_str_length);
            }
            System.out.println(msg);
        }
    }

    public interface OnCallBackListener<t,p,q> extends Serializable {
         void OnCallBackFirst(t... params);
         void OnCallBackSecond(p... params);
         void OnCallBackThrid(q... params);
    }

    public static class OnCallBackListenerImpl<t> implements OnCallBackListener<t,t,t>{

        @Override
        public  void OnCallBackFirst(t... params) {

        }

        @Override
        public  void OnCallBackSecond(t... params) {

        }

        @Override
        public void OnCallBackThrid(t... params) {

        }
    }

    public static class OnCallBackListenerImpl2<t,p> implements OnCallBackListener<t,p, Object>{

        @Override
        public  void OnCallBackFirst(t... params) {

        }

        @Override
        public  void OnCallBackSecond(p... params) {

        }

        @Override
        public void OnCallBackThrid(Object... params) {

        }
    }

}
