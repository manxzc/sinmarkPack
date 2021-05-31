package com.zcxie.zc.model_comm.util;

import com.tencent.mmkv.MMKV;

public abstract   class MMKVEntity<T> {
    final T mDefaultValue;

    private MMKVHelper mmkvHelper;

    final String mKey;

    protected MMKVEntity(MMKVHelper mmkvHelper,String key,T defaultValue){
        this.mmkvHelper=mmkvHelper;
        mKey=key;
        mDefaultValue=defaultValue;
    }
    public final boolean exists() {
        return mmkvHelper.open().contains(mKey);
    }
    public final MMKVEntity put(T value) {
         write(value);
        return this;
    }
    private void write(T value){
        write(   mmkvHelper.open(),value);
    }
    public final T get(){
        return  read(mmkvHelper.open());
    }
    public void remove(String key){
        mmkvHelper.open().remove(key);
    }
    public abstract T read(MMKV mmkv);
    public abstract void write(MMKV mmkv,T value);
}
