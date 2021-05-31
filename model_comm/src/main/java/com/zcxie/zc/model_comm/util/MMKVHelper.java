package com.zcxie.zc.model_comm.util;

import com.tencent.mmkv.MMKV;

import java.util.Set;

public class MMKVHelper {

    private MMKV  mmkv;

    private final int mMode;

    private final String mName;

    public MMKVHelper(int mMode, String mName) {
        this.mMode = mMode;
        this.mName = mName;
    }
    public MMKV getMmkv() {
        return mmkv;
    }
    public MMKV open() {
        if(mmkv == null) {
            mmkv = MMKV.defaultMMKV(mMode,mName);
        }
        return mmkv;
    }
    /**
     * 清空所有数据
     */
    public void clearAll() {
        mmkv.clearAll();
    }

    public MMKVEntity<Integer> create(String key, Integer value){

        return new MMKVEntity<Integer>(this, key, value) {
            @Override
            public Integer read(MMKV mmkv) {
                if (mmkv.contains(mKey)) {
                    return mmkv.getInt(mKey, 0);
                }
                return mDefaultValue;
            }

            @Override
            public void write(MMKV mmkv, Integer value) {
                if (value == null) {
                    throw new IllegalArgumentException("null cannot be written for <Integer>");
                }
                mmkv.putInt(mKey, value);
            }
        };
    }
    public MMKVEntity<Float> create(String key, float value){

        return new MMKVEntity<Float>(this, key, value) {
            @Override
            public Float read(MMKV mmkv) {
                if (mmkv.contains(mKey)) {
                    return mmkv.getFloat(mKey, 0);
                }
                return mDefaultValue;
            }

            @Override
            public void write(MMKV mmkv, Float value) {
                if (value == null) {
                    throw new IllegalArgumentException("null cannot be written for <Integer>");
                }
                mmkv.putFloat(mKey, value);
            }
        };
    }
    public MMKVEntity<Long> create(String key, long value){

        return new MMKVEntity<Long>(this, key, value) {
            @Override
            public Long read(MMKV mmkv) {
                if (mmkv.contains(mKey)) {
                    return mmkv.getLong(mKey, 0);
                }
                return mDefaultValue;
            }

            @Override
            public void write(MMKV mmkv, Long value) {
                if (value == null) {
                    throw new IllegalArgumentException("null cannot be written for <Integer>");
                }
                mmkv.putLong(mKey, value);
            }
        };
    }
    public MMKVEntity<byte[]> create(String key, byte[] value){

        return new MMKVEntity<byte[]>(this, key, value) {
            @Override
            public byte[] read(MMKV mmkv) {
                if (mmkv.contains(mKey)) {
                    return mmkv.getBytes(mKey, null);
                }
                return mDefaultValue;
            }

            @Override
            public void write(MMKV mmkv, byte[] value) {
                if (value == null) {
                    throw new IllegalArgumentException("null cannot be written for <Integer>");
                }
                mmkv.putBytes(mKey, value);
            }
        };
    }
    public MMKVEntity<Boolean> create(String key, boolean value){

        return new MMKVEntity<Boolean>(this, key, value) {
            @Override
            public Boolean read(MMKV mmkv) {
                if (mmkv.contains(mKey)) {
                    return mmkv.getBoolean(mKey, false);
                }
                return mDefaultValue;
            }

            @Override
            public void write(MMKV mmkv, Boolean value) {

                mmkv.putBoolean(mKey, value);
            }
        };
    }
//    public MMKVEntity<Parcelable> create(String key, Parcelable value){
//
//        return new MMKVEntity<Parcelable>(this, key, value) {
//            @Override
//            public Parcelable read(MMKV mmkv) {
//                if (mmkv.contains(mKey)) {
//                    return mmkv.get(mKey, 0);
//                }
//                return mDefaultValue;
//            }
//
//            @Override
//            public void write(MMKV mmkv, Integer value) {
//                if (value == null) {
//                    throw new IllegalArgumentException("null cannot be written for <Integer>");
//                }
//                mmkv.putInt(mKey, value);
//            }
//        };
//    }
    public MMKVEntity<Set<String>> create(String key, Set<String> value){

        return new MMKVEntity<Set<String>>(this, key, value) {
            @Override
            public Set read(MMKV mmkv) {
                if (mmkv.contains(mKey)) {
                    return mmkv.getStringSet(mKey, null);
                }
                return mDefaultValue;
            }

            @Override
            public void write(MMKV mmkv, Set value) {
                if (value == null) {
                    throw new IllegalArgumentException("null cannot be written for <Integer>");
                }
                mmkv.putStringSet(mKey, value);
            }
        };
    }
    public MMKVEntity<String> create(String key, String value){

        return new MMKVEntity<String>(this, key, value) {
            @Override
            public String read(MMKV mmkv) {
                if (mmkv.contains(mKey)) {
                    return mmkv.getString(mKey, "0");
                }
                return mDefaultValue;
            }

            @Override
            public void write(MMKV mmkv, String value) {
                if (value == null) {
                    throw new IllegalArgumentException("null cannot be written for <Integer>");
                }
                mmkv.putString(mKey, value);
            }
        };
    }

}
