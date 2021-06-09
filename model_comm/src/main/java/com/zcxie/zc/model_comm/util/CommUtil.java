package com.zcxie.zc.model_comm.util;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Environment;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import com.zcxie.zc.model_comm.R;
import com.zcxie.zc.model_comm.base.BaseApplication;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 所有的工具类集合
 */
public class CommUtil<T> {

    public static void closeKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
    }

    /**
     * 判断是否有网络
     *
     * @param
     * @return
     */
    public static boolean isNetWorkConnected() {
        ConnectivityManager mConnectivityManager = (ConnectivityManager) BaseApplication.Companion.getApplication().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        if (mNetworkInfo != null) {
            return mNetworkInfo.isAvailable() && mNetworkInfo.isConnected();
        }
        return false;
    }

    public static String addZeroPrefix(int hours) {
        return String.valueOf(hours).length() > 1 ? String.valueOf(hours) : "0" + String.valueOf(hours);
    }

    /**
     * 获取mipmap图标
     *
     * @param context
     * @param mipmapId
     * @return
     */
    public static Drawable getDrawableToMipmap(Context context, int mipmapId) {
        return context.getResources().getDrawable(mipmapId);
    }


    /**
     * 不带后缀的文件夹名字
     *
     * @return 返回路径（全路径）
     * @dirName 文件夹名称
     */
    public static File getFilesDir(String dirName) {
        File file = new File(getFilesDirSystem() + File.separator + dirName);
        if (!file.exists())
            file.mkdir();

        return file;
    }
    public static String getIMEI() {
        String imei= Build.SERIAL;
        return imei;
    }
    /**
     * 获取手机型号
     *
     * @return
     */
    public static String getPhoneModel() {
        return Build.MODEL+"_"+ Build.VERSION.SDK+"_"+ Build.VERSION.RELEASE;
    }

    public static File getFilesDirSystem() {
        String externalStorageState;
        Context context = BaseApplication.Companion.getApplication();
        try {
            externalStorageState = Environment.getExternalStorageState();
        } catch (NullPointerException var5) {
            externalStorageState = "";
        } catch (IncompatibleClassChangeError var6) {
            externalStorageState = "";
        }
        File folder = null;
        if (Environment.MEDIA_MOUNTED.equals(externalStorageState) && context != null
                && PackageManager.PERMISSION_GRANTED == context.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE")
                && PackageManager.PERMISSION_GRANTED == context.checkCallingOrSelfPermission("android.permission.READ_EXTERNAL_STORAGE")) {
            folder = context.getExternalCacheDir();
        }

        if (folder == null || !folder.exists()) {
            folder = BaseApplication.Companion.getApplication().getFilesDir();
        }
        return folder;
    }

    /**
     * 获取XML中字符串String.xml
     *
     * @return
     */
    public static String getStringToStringXml(Context context, int stringId) {
        if (stringId == 0) return "";
        return context.getResources().getString(stringId);
    }

    public static int getPxToDimenXml(Context context, int dimenId) {
        return context.getResources().getDimensionPixelSize(dimenId);
    }

    public static int getPxToColorXml(Context context, int colorId) {
        return context.getResources().getColor(colorId);
    }


    /**
     * 设置TextView文本
     *
     * @param textView
     * @param tv
     */
    public static void textViewTv(TextView textView, String tv) {
        textView.setText(tv);
    }

    /**
     * 根据Xml设置TextView文本
     *
     * @param context
     * @param textView
     * @param tvToXml
     */
    public static void textTextToStringXml(Context context, TextView textView, int tvToXml) {
        textView.setText(getStringToStringXml(context, tvToXml));
    }

    /**
     * 设置ImageView图标
     *
     * @param imageView
     * @param ivToMipmapXml
     */
    public static void imageViewIcon(ImageView imageView, int ivToMipmapXml) {
        if (imageView.getVisibility() == View.GONE) {
            imageView.setVisibility(View.VISIBLE);
        }
        imageView.setImageResource(ivToMipmapXml);
    }


    public static int currentOffset;


    /**
     * Toast多次点击只显示一次
     */
    public static class ToastU {
        private static Context context = null;
        private static Toast toast = null;

        public static void showToast(Context context, String text) {
            if (toast == null) {
                toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            } else {
                toast.setText(text);
                toast.setDuration(Toast.LENGTH_SHORT);
            }

            toast.show();
        }

        public static void showCenterToast(Context context, String text) {
            if (toast == null) {
                toast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
            } else {
                toast.setText(text);
                toast.setDuration(Toast.LENGTH_SHORT);
            }
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

        public static void showToast(String text) {
            if (toast == null) {
                toast = Toast.makeText(BaseApplication.Companion.getApplication(), text, Toast.LENGTH_SHORT);
            }
            toast.setText(text);
            toast.setDuration(Toast.LENGTH_SHORT);

            LinearLayout linearLayout = (LinearLayout) toast.getView();
            TextView messageTextView = (TextView) linearLayout.getChildAt(0);
            messageTextView.setTextSize(20);
            toast.setGravity(Gravity.CENTER, 12, 20);
            toast.show();
        }

        //            * 将Toast封装在一个方法中，在其它地方使用时直接输入要弹出的内容即可
//      */
        public static void ToastMessage(Context context, String messages) {
            //LayoutInflater的作用：对于一个没有被载入或者想要动态载入的界面，都需要LayoutInflater.inflate()来载入，LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();//调用Activity的getLayoutInflater()
            View view = inflater.inflate(R.layout.toast_style_view, null); //加載layout下的布局
            TextView text = view.findViewById(R.id.progress_message);
            text.setText(messages); //toast内容
            Toast toast = new Toast(BaseApplication.Companion.getApplication());
            toast.setGravity(Gravity.CENTER, 12, 20);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
            toast.setDuration(Toast.LENGTH_LONG);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
            toast.setView(view); //添加视图文件
            toast.show();
        }

        public static void ToastMsg(Context context, String messages) {
            //LayoutInflater的作用：对于一个没有被载入或者想要动态载入的界面，都需要LayoutInflater.inflate()来载入，LayoutInflater是用来找res/layout/下的xml布局文件，并且实例化
            LayoutInflater inflater = ((Activity) context).getLayoutInflater();//调用Activity的getLayoutInflater()
            View view = inflater.inflate(R.layout.toast_style_view, null); //加載layout下的布局
            TextView text = view.findViewById(R.id.progress_message);
            text.setText(messages); //toast内容
            Toast toast = new Toast(BaseApplication.Companion.getApplication());
            toast.setGravity(Gravity.CENTER, 12, 20);//setGravity用来设置Toast显示的位置，相当于xml中的android:gravity或android:layout_gravity
            toast.setDuration(Toast.LENGTH_SHORT);//setDuration方法：设置持续时间，以毫秒为单位。该方法是设置补间动画时间长度的主要方法
            toast.setView(view); //添加视图文件
            toast.show();
        }
    }


    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth1(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;         // 屏幕宽度（像素）
        //int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        //int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        //int screenHeight = (int) (height / density);// 屏幕高度(dp)
        return screenWidth;
    }

    public static int getScreenWidth() {
        return getDisplayMetrics().widthPixels;
    }

    /**
     * Returns a valid DisplayMetrics object
     *
     * @param
     * @return DisplayMetrics object
     */
    private static DisplayMetrics getDisplayMetrics() {
        final WindowManager
                windowManager =
                (WindowManager) BaseApplication.Companion.getApplication().getSystemService(Context.WINDOW_SERVICE);
        final DisplayMetrics metrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(metrics);
        return metrics;
    }

    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        //int width = dm.widthPixels;         // 屏幕宽度（像素）
        int height = dm.heightPixels;       // 屏幕高度（像素）
        float density = dm.density;         // 屏幕密度（0.75 / 1.0 / 1.5）
        //int densityDpi = dm.densityDpi;     // 屏幕密度dpi（120 / 160 / 240）
        // 屏幕宽度算法:屏幕宽度（像素）/屏幕密度
        //int screenWidth = (int) (width / density);  // 屏幕宽度(dp)
        int screenHeight = (int) (height / density);// 屏幕高度(dp)
        return screenHeight;
    }

    /**
     * 打开本地assetTxt文件
     *
     * @param context
     * @param fileName
     * @return
     */
    public static String readAssetsTxt(Context context, String fileName) {
        try {
            //Return an AssetManager instance for your application's package
            InputStream is = context.getAssets().open(fileName + ".txt");
            int size = is.available();
            // Read the entire asset into a local byte buffer.
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            // Convert the buffer into a string.
            String text = new String(buffer, "Unicode");
            // Finally stick the string into the text view.
            return text;
        } catch (IOException e) {
            // Should never happen!
//            throw new RuntimeException(e);
            e.printStackTrace();
        }
        return "读取错误，请检查文件名";
    }

    /**
     * 日期转换
     *
     * @param str
     * @return
     */
    public static String formatDate(String str) {
        SimpleDateFormat sf1 = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat sf2 = new SimpleDateFormat("MM-dd");
        String formatStr = "";
        try {
            formatStr = sf2.format(sf1.parse(str));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return formatStr;
    }
    /**
     * 获取当前时间
     *
     * @return
     */
    //把字符串转为日期
    public static Date ConverToDate(String strDate)
    {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            return df.parse(strDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getTime(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    public static String getTimebyStamp(long stamp) {//可根据需要自行截取数据显示
        Date date=new Date(stamp);
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    public static String getTimeHMS(Date date) {//可根据需要自行截取数据显示
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(date);
    }

    /**
     * 编辑对话框
     *
     * @param context
     * @param layoutId
     * @param bgColor
     * @return
     */
    public static Dialog showEditDialog(Context context, int layoutId, int bgColor) {
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(bgColor);
        //dialog.show();
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int displayWidth = dm.widthPixels;
        int displayHeight = dm.heightPixels;
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.width = (int) (displayWidth * 0.90);    //宽度设置为屏幕的0.5
        p.height = (int) (displayHeight * 0.38);    //宽度设置为屏幕的0.5
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        dialog.getWindow().setAttributes(p);     //设置生效
        return dialog;
    }


    /**
     * 对话框
     *
     * @param context
     * @param layoutId
     * @param bgColor
     * @param widthPercent
     * @param heightPercent
     * @return
     */
    public static Dialog showEditDialog(Context context, int layoutId, int bgColor, double widthPercent, double heightPercent) {
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        if (bgColor != 0) {
            dialog.getWindow().setBackgroundDrawableResource(bgColor);
        }
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int displayWidth = dm.widthPixels;
        int displayHeight = dm.heightPixels;
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.width = (int) (displayWidth * widthPercent);    //宽度设置为屏幕的0.5
        p.height = (int) (displayHeight * heightPercent);    //宽度设置为屏幕的0.5
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        dialog.getWindow().setAttributes(p);     //设置生效
        return dialog;
    }

    public static Dialog showEditDialog(Context context, int layoutId, int bgColor, double widthPercent, double heightPercent, boolean isCancel) {
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        Dialog dialog = new Dialog(context);
        dialog.setCancelable(isCancel);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(bgColor);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int displayWidth = dm.widthPixels;
        int displayHeight = dm.heightPixels;
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
        p.width = (int) (displayWidth * widthPercent);    //宽度设置为屏幕的0.5
        p.height = (int) (displayHeight * heightPercent);    //宽度设置为屏幕的0.5
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        dialog.getWindow().setAttributes(p);     //设置生效
        return dialog;
    }

    public static Dialog showEditDialog(Context context, int layoutId, int bgColor, boolean isCancel) {
        View view = LayoutInflater.from(context).inflate(layoutId, null);
        Dialog dialog = new Dialog(context);
        dialog.setCancelable(isCancel);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawableResource(bgColor);
        DisplayMetrics dm = context.getResources().getDisplayMetrics();
        int displayWidth = dm.widthPixels;
        int displayHeight = dm.heightPixels;
        WindowManager.LayoutParams p = dialog.getWindow().getAttributes();  //获取对话框当前的参数值
        dialog.setCanceledOnTouchOutside(false);// 设置点击屏幕Dialog不消失
        dialog.getWindow().setAttributes(p);     //设置生效
        return dialog;
    }


    /**
     * 存储对象
     * /data/data/<package name>/files
     *
     * @param context
     * @param ser
     * @param file
     * @return
     */
    public static boolean saveObject(Context context, Serializable ser, String file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = context.openFileOutput(file, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try {
                oos.close();
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取对象
     *
     * @param context
     * @param file
     * @return
     */
    public static Serializable readObject(Context context, String file) {
        if (!isExistDataCache(context, file))
            return null;
        FileInputStream fis = null;
        ObjectInputStream ois = null;
        try {
            fis = context.openFileInput(file);
            ois = new ObjectInputStream(fis);
            return (Serializable) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            // 反序列化失败 - 删除缓存文件
            if (e instanceof InvalidClassException) {
                File data = context.getFileStreamPath(file);
                data.delete();
            }
        } finally {
            try {
                ois.close();
                fis.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    /**
     * 判断缓存是否存在
     *
     * @param cachefile
     * @return
     */
    public static boolean isExistDataCache(Context context, String cachefile) {
        if (context == null)
            return false;
        boolean exist = false;
        File data = context.getFileStreamPath(cachefile);
        if (data.exists())
            exist = true;
        return exist;
    }

    /**
     * 验证手机号码是否合法 * 176, 177, 178; * 180, 181, 182, 183, 184, 185, 186, 187, 188, 189; * 145, 147; * 130, 131, 132, 133, 134, 135, 136, 137, 138, 139; * 150, 151, 152, 153, 155, 156, 157, 158, 159;
     * * * "13"代表前两位为数字13, * "[0-9]"代表第二位可以为0-9中的一个, * "[^4]" 代表除了4 * "\\d{8}"代表后面是可以是0～9的数字, 有8位。
     */
    public static boolean isMobileNumber(String mobiles) {
        String telRegex = "^((13[0-9])|(15[^4])|(18[0-9])|(17[0-8])|(147,145))\\d{8}$";
        return !TextUtils.isEmpty(mobiles) && mobiles.matches(telRegex);
    }

    /**
     * 手机号码用空格隔开，例如：133 2569 7536
     *
     * @return
     */
    public static String dealPhoneNumber(String phoneNum) {
        StringBuilder builder = new StringBuilder();
        if (!TextUtils.isEmpty(phoneNum)) {
            int phoneNumLength = phoneNum.length();
            for (int i = 0; i < phoneNumLength; i++) {
                builder.append(phoneNum.charAt(i));
                if (i == 2 || i == 6) {
                    builder.append(" ");
                }
            }
        }
        return builder.toString();
    }

    /**
     * 当前字符串是否匹配当前正则表达式
     *
     * @param matchStr
     * @param regex
     * @return
     */
    public static boolean isMatch(String matchStr, String regex) {
        return !TextUtils.isEmpty(matchStr) && matchStr.matches(regex);
    }

    /**
     * String类型转成Int类型
     *
     * @param s
     * @return
     */
    public static int stringToInt(String s) {
        return Integer.valueOf(s).intValue();
    }

    /**
     * String类型转成Double类型
     *
     * @param s
     * @return
     */
    public static double stringToDouble(String s) {
        return Double.parseDouble(s);
    }

    /**
     * 一种格式日期转换成另外一种格式日期字符串
     * 例如：201-10-18转成10月10日
     *
     * @param timeFormat
     * @param timeS
     * @return
     */
    public static String timeFormat(String timeFormat, String otherTimeFormat, String timeS) {
        try {
            SimpleDateFormat format = new SimpleDateFormat(otherTimeFormat);
            Date date = new Date(dateToStamp(timeS, timeFormat));
            return format.format(date).toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 日期转时间戳
     *
     * @param s
     * @return
     */
    public static long dateToStamp(String s, String timeFormat) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(timeFormat);
        Date date = null;
        try {
            date = simpleDateFormat.parse(s);
            return date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
            return -1;
        }
    }

    /**
     * 时间戳转日期
     *
     * @param s
     * @return
     */
    public static String stampTodate(int s) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("mm:ss");
        long lt = s;
        Date date = new Date(lt);
        res = simpleDateFormat.format(date);
        return res;
    }

    public static String dateToStr(Date date) {
        String res;
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        res = simpleDateFormat.format(date);
        return res;
    }

    /**
     * 保留2位小数
     *
     * @param s
     * @return
     */
    public static String keepTwo(String s) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String distanceString = decimalFormat.format(Double.parseDouble(s));//format 返回的是字符串
        return distanceString;
    }

    public static String keepOne(String s) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String distanceString = decimalFormat.format(Double.parseDouble(s));//format 返回的是字符串
        return distanceString;
    }
    public static String keepOne(float s) {
        DecimalFormat decimalFormat = new DecimalFormat("0.0");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String distanceString = decimalFormat.format(s);//format 返回的是字符串
        return distanceString;
    }
    public static String keepTwo(float s) {
        DecimalFormat decimalFormat = new DecimalFormat("0.00");//构造方法的字符格式这里如果小数不足2位,会以0补足.
        String distanceString = decimalFormat.format(s);//format 返回的是字符串
        return distanceString;
    }


    /**
     * 将每三个数字加上逗号处理（通常使用金额方面的编辑）
     * 价格、每隔3位加入逗号（，号）
     *
     * @param str 需要处理的字符串
     * @return 处理完之后的字符串
     */

    public static String priceAddComma(String str) {
        DecimalFormat decimalFormat = new DecimalFormat(",###");
        return decimalFormat.format(Double.parseDouble(str));
    }

    //软键盘弹出
    public static void showKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            view.requestFocus();
            imm.showSoftInput(view, 0);
        }
    }
    //软键盘隐藏
    public static void hideKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }

    public static void toggleSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) view.getContext()
                .getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.toggleSoftInput(0, InputMethodManager.SHOW_FORCED);
        }
    }

    /**
     * 获取应用版本号
     *
     * @param
     * @return
     */
    public static int getPackageCode() {
        PackageManager manager =BaseApplication.Companion.getApplication().getPackageManager();
        int code = 0;
        try {
            PackageInfo info = manager.getPackageInfo(BaseApplication.Companion.getApplication().getPackageName(), 0);
            code = info.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return code;
    }

    /**
     * 获取应用版本号
     *
     * @param
     * @return
     */
    public static String getPackageName() {
        PackageManager manager = BaseApplication.Companion.getApplication().getPackageManager();
        String name = "";
        try {
            PackageInfo info = manager.getPackageInfo(BaseApplication.Companion.getApplication().getPackageName(), 0);
            name = info.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return name;
    }

    /**
     * 获取版本号内容
     *
     * @param content
     * @return
     */
    public static String getUpateApkContent(String content) {
        try {
            String[] s = content.split("#");
            StringBuffer sb = new StringBuffer();
            int sLength = s.length;
            for (int i = 0; i < sLength; i++) {
                sb.append("-");
                sb.append(s[i]);
                if (i < sLength - 1)
                    sb.append("\n");
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = BaseApplication.Companion.getApplication().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static float dipTopx(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (dpValue * scale + 0.5f);
    }

//    public static void dealFlipperView(ViewFlipper mVp, final List<HomeIndexBean.NoticeListBean> mList, final Context mContext, final CallBackObj callBack) {
//        for (int i = 0; i < mList.size(); i++) {
//            TextView view = new TextView(mContext);
//            ViewFlipper.LayoutParams params = new ViewFlipper.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//            params.gravity = Gravity.LEFT | Gravity.CENTER_VERTICAL;
//            view.setLayoutParams(params);
//            view.setText(mList.get(i).getNoticeTitle());
//            view.setTextSize(12);
//            view.setEllipsize(TextUtils.TruncateAt.END);
//            view.setMaxEms(20);
//            view.setSingleLine();
//            view.setTextColor(mContext.getResources().getColor(R.color.color_999999));
//            final int finalI = i;
//            view.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    callBack.callBack(mList.get(finalI));
//                }
//            });
//            mVp.addView(view);
//        }
//        //是否自动开始滚动
//        mVp.setAutoStart(true);
//        //滚动时间
//        mVp.setFlipInterval(2000);
//        if (mList.size() > 1) {
//            //开始滚动
//            mVp.startFlipping();
//        }
//        //出入动画
//        mVp.setOutAnimation(mContext, R.anim.push_up_out);
//        mVp.setInAnimation(mContext, R.anim.push_down_in);
//    }


    /**
     * 方法描述 隐藏手机号中间位置字符，显示前三后三个字符
     *
     * @param phoneNo
     * @return
     * @author yaomy
     * @date 2018年4月3日 上午10:38:51
     */
    public static String hidePhoneNo(String phoneNo) {
        /*if(StringUtils.isBlank(phoneNo)) {
            return phoneNo;
        }*/

        int length = phoneNo.length();
        int beforeLength = 3;
        int afterLength = 4;
        //替换字符串，当前使用“*”
        String replaceSymbol = "*";
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < length; i++) {
            if (i < beforeLength || i >= (length - afterLength)) {
                sb.append(phoneNo.charAt(i));
            } else {
                sb.append(replaceSymbol);
            }
        }

        return sb.toString();
    }

    public static String getCurrentTime() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(new Date());
    }

    public static String getCurrentTimeHM() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return df.format(new Date());
    }

    public static String getCurrentTimeYMD() {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(new Date());
    }
    public static String getCurrentTime2File() {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
        return df.format(new Date());
    }
//    // 获取渠道号
//    public static String getChannel(Context mContext) {
//        String channel = "未知";
//        if (null == mContext) {
//            mContext = App.getAppContext();
//        }
//        try {
//            if (null == mContext) {
//                return channel;
//            }
//            PackageManager packageManager = mContext.getPackageManager();
//            if (null != packageManager) {
//                ApplicationInfo info = packageManager.getApplicationInfo(mContext.getPackageName(), PackageManager.GET_META_DATA);
//                if (info != null && info.metaData != null) {
//                    String metaData = info.metaData.getString("UMENG_CHANNEL");
//                    if (null != metaData) {
//                        channel = metaData;
//                    }
//                }
//            }
//
//        } catch (PackageManager.NameNotFoundException e) {
//            e.printStackTrace();
//            channel = "未知";
//        }
//        return channel;
//    }

    /*
     * 如果是小数，保留两位，非小数，保留整数
     * @param number
     */
    public static String getDoubleString(double number) {
        String numberStr;
        if (((int) number * 1000) == (int) (number * 1000)) {
            //如果是一个整数
            numberStr = String.valueOf((int) number);
        } else {
            DecimalFormat df = new DecimalFormat("#,##0.00");//######
            numberStr = df.format(number);
        }
        return numberStr;
    }

    public static String getDouble2String(String numStr) {
        double number = Double.valueOf(numStr);
        DecimalFormat df = new DecimalFormat("0.00");
        return df.format(number);
    }

    //    判断字符串是否为数字
    public static boolean isNumeric(String str) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher isNum = pattern.matcher(str);
        if (!isNum.matches()) {
            return false;
        }
        return true;
    }

    @SuppressLint("MissingPermission")
    public static String getMyUUID(Context context) {
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, tmPhone, androidId;
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return TODO;
//        }
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }

    @SuppressLint("MissingPermission")
    public static String getMyUUID() {
        final TelephonyManager tm = (TelephonyManager)BaseApplication.Companion.getApplication().getSystemService(Context.TELEPHONY_SERVICE);
        final String tmDevice, tmSerial, tmPhone, androidId;
//        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
//            // TODO: Consider calling
//            //    ActivityCompat#requestPermissions
//            // here to request the missing permissions, and then overriding
//            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//            //                                          int[] grantResults)
//            // to handle the case where the user grants the permission. See the documentation
//            // for ActivityCompat#requestPermissions for more details.
//            return TODO;
//        }
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(BaseApplication.Companion.getApplication().getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);
        UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        String uniqueId = deviceUuid.toString();
        return uniqueId;
    }

    /**
     * 判断应用是否已经启动
     *
     * @param context 一个context
     * @param
     * @return boolean
     */
    public static boolean isAppAlive(Context context) {
        ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        List<ActivityManager.RunningTaskInfo> list = am.getRunningTasks(100);
        for (ActivityManager.RunningTaskInfo info : list) {
            if (info.topActivity.getPackageName().equals("com.sina.shihui.baoku") && info.baseActivity.getPackageName().equals("com.sina.shihui.baoku")) {
                return true;
            }
        }
        return false;

//        100 表示取的最大的任务数，info.topActivity表示当前正在运行的Activity，info.baseActivity表系统后台有此进程在运行，
//        具体要做如何判断就看自已的业务需求。这个类还有更多的方法可以取得系统运行的服务、内存使用情况等的方法，请各位自行查找。
    }
//    public static List<T> swap(List<T> tList,int index1,int index2){
//        return  Collections.swap(tList,index1,index2);
//    }



    /**
     * 按制定长度切割list 集合
     * @param list
     * @param groupSize
     * @param <T>
     * @return
     */
    public static <T> List<List<T>> splitList(List<T> list, int groupSize){
        int length = list.size();
        // 计算可以分成多少组
        int num = ( length + groupSize - 1 )/groupSize ; // TODO
        List<List<T>> newList = new ArrayList<>(num);
        for (int i = 0; i < num; i++) {
            // 开始位置
            int fromIndex = i * groupSize;
            // 结束位置
            int toIndex = (i+1) * groupSize < length ? ( i+1 ) * groupSize : length ;
            newList.add(list.subList(fromIndex,toIndex)) ;
        }
        return  newList ;
    }
}
