package cn.ymade.module_home.vm

import android.app.Activity
import android.content.Intent
import android.device.DeviceManager
import android.util.Log
import cn.ymade.module_home.R
import com.alibaba.android.arouter.launcher.ARouter
import com.zcxie.zc.model_comm.base.BaseViewModel
import com.zcxie.zc.model_comm.util.AppConfig

class VMMain :BaseViewModel() {

    val CHECK_DEV=false  //检测devSn开关
    fun startMain(ctx: Activity){
            val token: String = AppConfig.Token.get()
            Log.i(
                    "activation ",
                    "run: token $token"
            )
            if ("UNKNOW" != token) {
                ARouter.getInstance().build("/home/homeActivity1").navigation()
            } else {
                var dvId=if (CHECK_DEV)"-1" else "debug"
                try {
                    val dvm:DeviceManager= DeviceManager()
                    dvId= dvm.deviceId
                }catch (e:Exception){

                }finally {
                    ARouter.getInstance().build("/login/loginActivity").withString("dvIds",dvId).navigation()
                }

            }
            ctx.finish()
    }
}