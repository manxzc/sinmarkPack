package com.zcxie.zc.model_comm.util;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.TextView;

import com.zcxie.zc.model_comm.callbacks.CallBack;


/**
 * Created by PC on 2018/12/15.
 */

public class EditViewUtil {
   private static long lastSysTime = 0L;
    private static long spaceTime = 50L;
    public static void EditDatachangeLister(EditText editText, final CallBack<String> callBack) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (null == s) {
                    callBack.callBack("");
                } else {
                    callBack.callBack(s.toString());
                }
            }
        });
    }
    public static void EditDatachangeListerTWO(EditText editText, final CallBack<String> callBack) {

        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (null == s) {
                    callBack.callBack("");
                } else {
                    callBack.callBack(s.toString());
                }
            }
        });
    }
    //监听回车按钮
    public static void EditActionListener(EditText editText, final CallBack<String> callBack) {
        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent keyEvent) {
//                if (actionId == EditorInfo.IME_NULL) {
//                    if (System.currentTimeMillis() - lastSysTime < spaceTime)
//                        return true;
//                    lastSysTime = System.currentTimeMillis();
//                    String str = v.getText().toString().trim();
//                    int oldStrLen = str.length();
//                    if (oldStrLen > poisition) {
//                        str = str.substring(poisition, oldStrLen);
//                        poisition = oldStrLen - poisition;
//                        setSearchetText(str);
//                        if (searchInputNext != null)
//                            searchInputNext.onNext(str);
//                    }
//                    return true;
//                }

                if (actionId == EditorInfo.IME_ACTION_NEXT ||
                        actionId == EditorInfo.IME_ACTION_DONE ||
                        actionId == EditorInfo.IME_ACTION_SEARCH ||
                        actionId == EditorInfo.IME_ACTION_SEND) {
                    if (System.currentTimeMillis() - lastSysTime < spaceTime)
                        return true;
                    lastSysTime = System.currentTimeMillis();
                    if (callBack != null)
                        callBack.callBack(v.getText().toString().trim());
                    return true;
                }




                return false;
            }
        });
    }

    public static boolean passwordCheck(String password, int left, int right) {
        if (password.isEmpty()) {
            return false;
        }
        return password.matches("([0-9]+[a-zA-Z]+|[a-zA-Z]+[0-9]+)[0-9a-zA-Z]*") && password.length() > left && password.length() < right;
    }


    public static void setInputType(EditText editText, String starKey, String endKey) {
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

}
