package com.zcxie.zc.model_comm.base

import android.app.Activity
import android.app.Application
import android.util.Log
import com.alibaba.android.arouter.launcher.ARouter
import com.tencent.mmkv.MMKV
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
        val rootDir:String= MMKV.initialize(this)
        Log.i(TAG, "onCreate: mmkv rootDir $rootDir")
    }
}