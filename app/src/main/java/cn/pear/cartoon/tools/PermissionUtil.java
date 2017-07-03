package cn.pear.cartoon.tools;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

import java.util.ArrayList;

/**
 * Created by liuliang on 2017/5/25.
 */

public class PermissionUtil {
    private static PermissionUtil permissionUtil;
    public ArrayList<String> permissionList;

    //构造方法中初始化集合
    private PermissionUtil() {
        permissionList =new ArrayList<>();
    }
    //单例模式获取PermissionUtil对象
    public static PermissionUtil getInstance(){

        if (permissionUtil == null) {
            synchronized (PermissionUtil.class) {
                if (permissionUtil == null) {
                    permissionUtil = new PermissionUtil();
                }
            }
        }
        return permissionUtil;
    }

    //申请需要的权限
    public void requestNeedPermission(Context context, String[] strings, int code){
        //如果api>23则集合中的申请权限
        if(strings.length>0){
            if(Build.VERSION.SDK_INT>=23){
                ActivityCompat.requestPermissions((Activity) context, strings,code);
            }
        }
    }

    //初始化的获取需要申请的权限数据
    public String[] checkPermissions(Context context){
        if((ContextCompat.checkSelfPermission(context, Manifest.permission.WRITE_EXTERNAL_STORAGE)== PackageManager.PERMISSION_DENIED)){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if((ContextCompat.checkSelfPermission(context,Manifest.permission.READ_PHONE_STATE)==PackageManager.PERMISSION_DENIED)){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if((ContextCompat.checkSelfPermission(context,Manifest.permission.CAMERA)==PackageManager.PERMISSION_DENIED)){
            permissionList.add(Manifest.permission.CAMERA);
        }

        String[] strings =new String[permissionList.size()];
        if(permissionList.size()!=0){
            for(int i=0;i<permissionList.size();i++){
                strings[i]= permissionList.get(i);
            }
        }
        permissionList.clear();
        return strings;
    }

    /* 摄像头权限*/
    public String[] checkCameraPermission(Context context){
        ArrayList<String> permissions =new ArrayList<>();
//        if((ContextCompat.checkSelfPermission(context,Manifest.permission.FLASHLIGHT)!=PackageManager.PERMISSION_GRANTED)){
//            permissions.add(Manifest.permission.FLASHLIGHT);
//        }
        if((ContextCompat.checkSelfPermission(context,Manifest.permission.CAMERA)!=PackageManager.PERMISSION_GRANTED)){
            permissions.add(Manifest.permission.CAMERA);
        }
        if(permissions.size()>0){
            String[] per =permissions.toArray(new String[permissions.size()]);
            return per;
        }else {
            return null;
        }
    }

}
