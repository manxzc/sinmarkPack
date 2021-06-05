package com.zcxie.zc.model_comm.util;

import android.content.Context;
import android.os.Build;

import java.util.UUID;

public class AppConfig {
    public static MMKVHelper mmkvHelper=new MMKVHelper( Context.MODE_PRIVATE,"packData");
    public static MMKVEntity<Boolean> Login = mmkvHelper.create("login", false);
    public static MMKVEntity<String> oaid = mmkvHelper.create("oaid", UUID.randomUUID().toString());

    public static MMKVEntity<String> Token = mmkvHelper.create("Token", "UNKNOW");

    public static MMKVEntity<Boolean> hasDevInfo = mmkvHelper.create("hasDevInfo", false);

    public static MMKVEntity<Boolean> hasStaff = mmkvHelper.create("hasStaff", false);
    public static MMKVEntity<String> staff = mmkvHelper.create("staffName", "--");

    public static MMKVEntity<Long> lotAutoSN = mmkvHelper.create("lotAutoSN",1);

    public static void clearAll() {
        mmkvHelper.clearAll();
    }
    public static String getIMEI() {
        String imei= Build.SERIAL;
        return imei;
    }

}
