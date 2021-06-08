package com.zcxie.zc.model_comm.util;

import android.content.Context;
import android.os.Build;

import java.util.UUID;

public class AppConfig {
//    public static MMKVHelper mmkvHelper=new MMKVHelper( Context.MODE_PRIVATE,"packData");
//    public static MMKVEntity<Boolean> Login = mmkvHelper.create("login", false);
//    public static MMKVEntity<String> oaid = mmkvHelper.create("oaid", UUID.randomUUID().toString());
//
//    public static MMKVEntity<String> Token = mmkvHelper.create("Token", "UNKNOW");
//
//    public static MMKVEntity<Boolean> hasDevInfo = mmkvHelper.create("hasDevInfo", false);
//
//    public static MMKVEntity<Boolean> hasStaff = mmkvHelper.create("hasStaff", false);
//    public static MMKVEntity<String> staff = mmkvHelper.create("staffName", "--");
//
//    public static MMKVEntity<Long> lotAutoSN = mmkvHelper.create("lotAutoSN",1);

    private static SPHelper sPrefs = new SPHelper("packDatas", Context.MODE_PRIVATE);
    public static SharedPreference<Boolean> Login = sPrefs.value("login", false);
    public static SharedPreference<String> oaid = sPrefs.value("oaid", UUID.randomUUID().toString());

    public static SharedPreference<String> Token = sPrefs.value("Token", "UNKNOW");

    public static SharedPreference<Boolean> hasDevInfo = sPrefs.value("hasDevInfo", false);

    public static SharedPreference<Boolean> hasStaff = sPrefs.value("hasStaff", false);
    public static SharedPreference<String> staff = sPrefs.value("staffName", "--");

    public static SharedPreference<Long> lotAutoSN = sPrefs.value("lotAutoSN",1l);

    public static void clearAll() {
//        mmkvHelper.clearAll();
        sPrefs.clearAll();
    }
    public static String getIMEI() {
        String imei= Build.SERIAL;
        return imei;
    }

}
