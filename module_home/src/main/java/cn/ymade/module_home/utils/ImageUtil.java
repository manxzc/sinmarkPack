package cn.ymade.module_home.utils;

import android.text.TextUtils;

import cn.ymade.module_home.R;

public class ImageUtil {

    public int getRequestIconByCheck(int statusInt) {
        String status= String.valueOf(statusInt);
        if (TextUtils.isEmpty(status)) return R.drawable.shape_noup_bg;
       else if (status.equals("0")) {
            return R.drawable.shape_noup_bg;
        } else if (status.equals("1")) {
            return R.drawable.shape_up_bg;
        }
        return R.drawable.shape_noup_bg;
    }
    public String getStrByCheck(int statusInt) {
        String status= String.valueOf(statusInt);
        if (TextUtils.isEmpty(status))
            return "未上传";
        else if (status.equals("0")) {
            return "已上传";
        } else if (status.equals("1")) {
            return "未上传";
        }
        return "未上传";

    }
}
