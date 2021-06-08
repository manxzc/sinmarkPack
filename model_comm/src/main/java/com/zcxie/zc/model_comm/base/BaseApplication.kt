package com.zcxie.zc.model_comm.base

import android.R.attr
import android.R.id
import android.app.Activity
import android.app.Application
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.mmkv.*
import com.zcxie.zc.model_comm.util.Utils


open class BaseApplication: Application() {
    val TAG=javaClass.simpleName
    val  actList= mutableListOf<Activity>()
    companion object {
        var application:BaseApplication?=null
        fun getInstance()= application
        val ROOT_PACKAGE:String="com.zcxie.zc"
    }
    fun closeAllAct(){
//        for (act in actList){
//            act.finish()
//        }
        actList.forEach {
            it.finish()
        }
    }
    override fun onCreate() {
        super.onCreate()
        application=this
        var isDebug = true

        if (isDebug) { // 这两行必须写在init之前，否则这些配置在init过程中将无效
            ARouter.openLog(); // 打印日志
            ARouter.openDebug(); // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        }
        ARouter.init(application); // 尽可能早，推荐在Application中初始化
        Utils.init(application)
//        val dir = filesDir.absolutePath + "/mmkv"
//        val rootDir:String= MMKV.initialize(this)
//        Log.i(TAG, "onCreate: mmkv rootDir $rootDir")

        initMMKV()
    }

    private fun initMMKV() {
        // set root dir
        //String rootDir = MMKV.initialize(this);
        // set root dir
        //String rootDir = MMKV.initialize(this);
//        val dir = filesDir.absolutePath + "/mmkv"
//        val rootDir = MMKV.initialize(
//            this, dir,
//            { libName -> ReLinker.loadLibrary(this, libName) }, MMKVLogLevel.LevelInfo
//        )
//        Log.i("MMKV", "mmkv root: $rootDir")
//
//        // set log level
//
//        // set log level
//        MMKV.setLogLevel(MMKVLogLevel.LevelInfo)
//
//        // you can turn off logging
//        //MMKV.setLogLevel(MMKVLogLevel.LevelNone);
//
//        // log redirecting & recover logic
//
//        // you can turn off logging
//        //MMKV.setLogLevel(MMKVLogLevel.LevelNone);
//
//        // log redirecting & recover logic
//        MMKV.registerHandler(this)
//
//        // content change notification
//
//        // content change notification
//        MMKV.registerContentChangeNotify(this)
    }
//
//    override fun onMMKVCRCCheckFail(mmapID: String?): MMKVRecoverStrategic {
//        return MMKVRecoverStrategic.OnErrorRecover;
//    }
//
//    override fun onMMKVFileLengthError(mmapID: String?): MMKVRecoverStrategic {
//        return MMKVRecoverStrategic.OnErrorRecover;
//    }
//
//    override fun wantLogRedirecting(): Boolean {
//        return true;
//    }
//
//    override fun mmkvLog(
//        level: MMKVLogLevel?,
//        file: String?,
//        line: Int,
//        function: String?,
//        message: String?
//    ) {
//        val log =
//            "<" + file.toString() + ":" + line.toString() + "::" + function.toString() + "> " + id.message
//
//    }
//
//    override fun onContentChangedByOuterProcess(mmapID: String?) {
//        Log.i("MMKV", "other process has changed content of : " + mmapID);
//    }
}