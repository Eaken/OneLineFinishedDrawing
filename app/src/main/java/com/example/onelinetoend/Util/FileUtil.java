package com.example.onelinetoend.Util;

import android.os.Environment;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.IdentityHashMap;
import java.util.Set;

public class FileUtil {


    public static String getBaseFilePath(){
        File file=new File(Environment.getExternalStorageDirectory()+"/"+ValueUtil.appName);
        try {
            if(!file.exists()){
                file.mkdirs();
            }
            return file.getAbsolutePath();
        }catch (Exception e){
            OtherUtil.DebuggerLog(e.getMessage());
            return Environment.getExternalStorageDirectory().getAbsolutePath();
        }
    }


    public static File getLogFile(){
        File file=new File(getBaseFilePath()+"/Log","log.txt");
        try {
            if(!file.exists()){
                file.getParentFile().mkdirs();
                file.createNewFile();
            }
            return file;
        }catch (Exception e){
            OtherUtil.DebuggerLog(e.getMessage());
            return null;
        }
    }


    public static void wirteToFile(File fileWrite, String string, boolean isAppend) {
        if(fileWrite==null) return;

        try {
            // 首先判断文件是否存在
            if (!fileWrite.exists()) {
                fileWrite.getParentFile().mkdirs();
                if (!fileWrite.createNewFile()) {   // 文件不存在则创建文件
                    OtherUtil.DebuggerLog("wirteToFile：创建文件失败");
                    return;
                }
            }
            FileWriter filerWriter = new FileWriter(fileWrite, isAppend);// 后面这个参数代表是不是要接上文件中原来的数据，不进行覆盖
            BufferedWriter bufWriter = new BufferedWriter(filerWriter);

            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");// HH:mm:ss
            //获取当前时间
            Date date = new Date(System.currentTimeMillis());
            bufWriter.write("\n-------------------------------" +
                    "\n-------------------------------" +
                    "\n-------------------------------" +
                    "\n-------------------------------" +
                    "\n-------------------------------" +
                    "\n"+simpleDateFormat.format(date)+":\n");
            bufWriter.write(string);
            bufWriter.newLine();
            bufWriter.close();
            filerWriter.close();
        }catch (Exception e){
            OtherUtil.DebuggerLog("wirteToFile：输出文件失败\n"+e.getMessage());
        }

    }

    public static void writeThrowleToLog(Throwable throwable){
        if(PermissionUtil.checkSavePermission(ActivityUtil.getInstance().getCurActivity(),100)){
            File logFile = FileUtil.getLogFile();
            if(logFile!=null){
                StringBuilder logStringBuilder = new StringBuilder();
                Set<Throwable> dejaVu =
                        Collections.newSetFromMap(new IdentityHashMap<>());
                dejaVu.add(throwable);

                // Print our stack trace
                logStringBuilder.append(throwable).append("\n");
                StackTraceElement[] trace = throwable.getStackTrace();
                for (StackTraceElement traceElement : trace){
                    logStringBuilder.append("\tat ").append(traceElement).append("\n");
                }

                // Print suppressed exceptions, if any
                for (Throwable se : throwable.getSuppressed()){
                    printEnclosedStackTrace(trace, "Suppressed: ", "\t", dejaVu,se,logStringBuilder);
                }

                // Print cause, if any
                Throwable ourCause = throwable.getCause();
                if (ourCause != null)
                    printEnclosedStackTrace(trace, "Caused by: ", "", dejaVu,ourCause,logStringBuilder);


                FileUtil.wirteToFile(logFile,logStringBuilder.toString(),true);
            }
        }
    }

    private static void printEnclosedStackTrace(StackTraceElement[] enclosingTrace,
                                                String caption,
                                                String prefix,
                                                Set<Throwable> dejaVu, Throwable throwable, StringBuilder stringBuilder) {
        if (dejaVu.contains(throwable)) {
            stringBuilder.append("\t[CIRCULAR REFERENCE:").append(throwable).append("]").append("\n");
        } else {
            dejaVu.add(throwable);
            // Compute number of frames in common between this and enclosing trace
            StackTraceElement[] trace = throwable.getStackTrace();
            int m = trace.length - 1;
            int n = enclosingTrace.length - 1;
            while (m >= 0 && n >=0 && trace[m].equals(enclosingTrace[n])) {
                m--; n--;
            }
            int framesInCommon = trace.length - 1 - m;

            // Print our stack trace
            stringBuilder.append(prefix).append(caption).append(throwable);
            for (int i = 0; i <= m; i++)
                stringBuilder.append(prefix).append("\tat ").append(trace[i]);
            if (framesInCommon != 0)
                stringBuilder.append(prefix).append("\t... ").append(framesInCommon).append(" more");

            // Print suppressed exceptions, if any
            for (Throwable se : throwable.getSuppressed())
                printEnclosedStackTrace( trace, "Suppressed: ",
                        prefix +"\t", dejaVu,se,stringBuilder);

            // Print cause, if any
            Throwable ourCause = throwable.getCause();
            if (ourCause != null)
                printEnclosedStackTrace(trace, "Caused by: ", prefix, dejaVu,ourCause,stringBuilder);
        }
    }

}
