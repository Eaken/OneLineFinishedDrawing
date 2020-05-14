package com.example.onelinetoend.Util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

public class PermissionUtil {

    public static boolean checkSavePermission(Activity activity, int requestCode){
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},requestCode);
            return false;
        }else {
            return true;
        }
    }

    public static boolean checkSavePermissionInFragment(@NonNull Fragment fragment, int requestCode){
        if(ContextCompat.checkSelfPermission(fragment.getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            fragment.requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},requestCode);
            return false;
        }else {
            return true;
        }
    }

    public static boolean checkCameraPermission(Activity activity, int requestCode){
        if(ContextCompat.checkSelfPermission(activity, Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,new String[]{Manifest.permission.CAMERA},requestCode);
            return false;
        }else {
            return true;
        }
    }

    public static boolean checkCameraPermissionInFragment(@NonNull Fragment fragment, int requestCode){
        if(ContextCompat.checkSelfPermission(fragment.getContext(), Manifest.permission.CAMERA)!= PackageManager.PERMISSION_GRANTED){
            fragment.requestPermissions(new String[]{Manifest.permission.CAMERA},requestCode);
            return false;
        }else {
            return true;
        }
    }
}
