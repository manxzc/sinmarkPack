package com.zcxie.zc.model_comm.util;

import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.WindowManager;

import com.zcxie.zc.model_comm.model.ScreenEntity;


public class ScreenManager {
    private Context context;
    WindowManager wm;


    public DisplayMetrics getDm() {
        return dm;
    }

    public void setDm(DisplayMetrics dm) {
        this.dm = dm;
    }

    DisplayMetrics dm;
    public ScreenManager(Context context){
        this.context=context;
        wm= (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
         dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
    }
    public ScreenEntity getAndroiodScreenProperty() {

        int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (height / density);// 屏幕高度(dp)

        ScreenEntity screenEntity=new ScreenEntity();
        screenEntity.setWidthPx(width);
        screenEntity.setHeightPx(height);
        screenEntity.setDensity(density);
        screenEntity.setScreenWidth(screenWidth);
        screenEntity.setScreenHeight(screenHeight);



        Log.d("h_bl", "屏幕宽度（像素）：" + width);
        Log.d("h_bl", "屏幕高度（像素）：" + height);
        Log.d("h_bl", "屏幕密度（0.75 / 1.0 / 1.5）：" + density);
        Log.d("h_bl", "屏幕密度dpi（120 / 160 / 240）：" + densityDpi);
        Log.d("h_bl", "屏幕宽度（dp）：" + screenWidth);
        Log.d("h_bl", "屏幕高度（dp）：" + screenHeight);
        return screenEntity;
    }


    public  int dp2Px( int dp) {

        float density = dm.density;
        int px = (int) (dp * density + 0.5f);
        return px;

    }



    public  int px2Dp(int px) {

        float density = dm.density;

        int dp = (int) (px / density + 0.5f);

        return dp;

    }
}
