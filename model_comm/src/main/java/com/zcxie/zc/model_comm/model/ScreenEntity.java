package com.zcxie.zc.model_comm.model;

import java.io.Serializable;

public class ScreenEntity implements Serializable {

    // 屏幕宽度（像素）
    // 屏幕高度（像素）
    // 屏幕密度（0.75 / 1.0 / 1.5）
    // 屏幕密度dpi（120 / 160 / 240）
    private int widthPx;
    private int heightPx;
    private float density;
    private int densityDpi;

    private int screenWidth ; // 屏幕宽度(dp)
    private int screenHeight ;// 屏幕高度(dp)


    public int getWidthPx() {
        return widthPx;
    }

    public void setWidthPx(int widthPx) {
        this.widthPx = widthPx;
    }

    public int getHeightPx() {
        return heightPx;
    }

    public void setHeightPx(int heightPx) {
        this.heightPx = heightPx;
    }

    public float getDensity() {
        return density;
    }

    public void setDensity(float density) {
        this.density = density;
    }

    public int getDensityDpi() {
        return densityDpi;
    }

    public void setDensityDpi(int densityDpi) {
        this.densityDpi = densityDpi;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void setScreenWidth(int screenWidth) {
        this.screenWidth = screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public void setScreenHeight(int screenHeight) {
        this.screenHeight = screenHeight;
    }

}
