package com.zcxie.zc.model_comm.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;

public class ApkUtils {
    private static final String TAG="ApkUtils";
    private static final String RootPath= Environment.getExternalStorageDirectory()+ File.separator+"pcpush"+ File.separator;
    public static synchronized boolean isAppInstalled(String uri, Context context) {   //判断apk是否安装
        PackageManager pm = context.getPackageManager();
        boolean installed =false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            installed =true;
        } catch(PackageManager.NameNotFoundException e) {
            installed =false;
        }
        return installed;
    }
    private synchronized void installApk(File file , String type, String version ) {
        String path=file.getAbsolutePath();
        Log.i(TAG, "installApk:  开始安装 "+path+" type "+type+" version "+version);
        try {
         String command = "pm install -r " + path;
            Process proc = Runtime.getRuntime().exec(new String[] { "sh", "-c", command });
            BufferedReader errorStream = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String msg = "";
            String line;
            // 读取命令的执行结果
            while ((line = errorStream.readLine()) != null) {
                msg += line;
            }
            if (!TextUtils.isEmpty(msg)&&msg.toLowerCase().contains("fail")) {
                Log.d("TAG", "testWifi installHud msg is " + msg);
            }
            Log.d("TAG", "testWifi installHud msg is " + msg);

            Log.i(TAG, "testWifi installHud: waitFor "+proc.waitFor());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d("TAG", "testWifi installHud Exception " + e.toString());
            Log.i(TAG, "testWifi installHud: "+e.toString());
        }
    }
    public static synchronized void installComApk(File file ) {
        String path=file.getAbsolutePath();
        Log.i(TAG, "installComApk:  开始安装 "+path);
        try {
//            String command = "pm install -r " + path;

            String cmd1= "am start -n com.baojiakeji.carbox/.activities.SplashActivity";
            String cmd2 = "pm install -r " + path + " && ";
            String cmd = cmd2 + cmd1;
            Process proc = Runtime.getRuntime().exec(new String[] { "su", "-c", cmd });
            BufferedReader errorStream = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String msg = "";
            String line;
            // 读取命令的执行结果
            while ((line = errorStream.readLine()) != null) {
                msg += line;
            }
            if (!TextUtils.isEmpty(msg)&&msg.toLowerCase().contains("fail")&&msg.contains("Exception")) {
                Log.d(TAG, " installComApk msg is fail " + msg);
            }
            Log.d(TAG, " installComApk msg is " + msg);
            Log.i(TAG, " : waitFor "+proc.waitFor());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "installComApk Exception " + e.toString());
            Log.i(TAG, "installComApk: "+e.toString());
        }
    }
    public static synchronized void installComApk(String appName ) {
        File file=new File(RootPath+appName);
        String path=file.getAbsolutePath();
        Log.i(TAG, "installComApk:  开始安装 "+path);
    try {
//            String command = "pm install -r " + path;

        String cmd1= "am start -n com.baojiakeji.carbox/.activities.SplashActivity";
        String cmd2 = "pm install -r " + path + " && ";
        String cmd = cmd2 + cmd1;
            Process proc = Runtime.getRuntime().exec(new String[] { "su", "-c", cmd });
            BufferedReader errorStream = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String msg = "";
            String line;
            // 读取命令的执行结果
            while ((line = errorStream.readLine()) != null) {
                msg += line;
            }
            if (!TextUtils.isEmpty(msg)&&msg.toLowerCase().contains("fail")&&msg.contains("Exception")) {
                Log.d(TAG, " installComApk msg is fail " + msg);
            }
            Log.d(TAG, " installComApk msg is " + msg);
            Log.i(TAG, " : waitFor "+proc.waitFor());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "installComApk Exception " + e.toString());
            Log.i(TAG, "installComApk: "+e.toString());
        }
    }
    public static synchronized void installApkFile(  File file ) {
        String path=file.getAbsolutePath();
        Log.i(TAG, "installComApk:  开始安装 "+path);
        try {
            String command = "pm install -r " + path;
            Process proc = Runtime.getRuntime().exec(new String[] { "su", "-c", command });
            BufferedReader errorStream = new BufferedReader(new InputStreamReader(proc.getErrorStream()));
            String msg = "";
            String line;
            // 读取命令的执行结果
            while ((line = errorStream.readLine()) != null) {
                msg += line;
            }
            if (!TextUtils.isEmpty(msg)&&msg.toLowerCase().contains("fail")&&msg.contains("Exception")) {
                Log.d(TAG, " installComApk msg is fail " + msg);
            }
            Log.d(TAG, " installComApk msg is " + msg);
            Log.i(TAG, " : waitFor "+proc.waitFor());
        } catch (Exception e) {
            e.printStackTrace();
            Log.d(TAG, "installComApk Exception " + e.toString());
            Log.i(TAG, "installComApk: "+e.toString());
        }
    }
}
