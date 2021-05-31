package com.zcxie.zc.model_comm.util;

import android.content.SharedPreferences;

/**
 *
 */

public abstract class SharedPreference<T> {

    final T mDefaultValue;

    private SPHelper mConfig;

    final String mKey;

    protected SharedPreference(SPHelper config, String key, T defaultValue) {
        mConfig = config;
        mKey = key;
        mDefaultValue = defaultValue;
    }


    public final boolean exists() {
        return mConfig.open().contains(mKey);
    }

    public final T get() {
        return read(mConfig.open());
    }

    public final String getKey() {
        return mKey;
    }

    private boolean commit(SharedPreferences.Editor editor) {
        editor.commit();
        return true;
    }

    public final void put(T value) {
        SharedPreferences sp = mConfig.open();
        SharedPreferences.Editor editor = sp.edit();
        write(editor, value);
        commit(editor);
    }

    public final void remove() {
        commit(mConfig.open().edit().remove(mKey));
    }

    protected abstract T read(SharedPreferences sp);

    protected abstract void write(SharedPreferences.Editor editor, T value);

}