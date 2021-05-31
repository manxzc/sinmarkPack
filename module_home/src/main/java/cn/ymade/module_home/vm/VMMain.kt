package cn.ymade.module_home.vm

import android.app.Activity
import android.content.Intent
import android.util.Log
import cn.ymade.module_home.ui.HomeActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.zcxie.zc.model_comm.base.BaseViewModel
import com.zcxie.zc.model_comm.util.AppConfig

class VMMain :BaseViewModel() {

    fun startMain(ctx: Activity){
            val token: String = AppConfig.Token.get()
            Log.i(
                    "activation ",
                    "run: token $token"
            )
            if ("UNKNOW" != token) {
                ARouter.getInstance().build("/home/homeActivity").navigation()
            } else {
                ARouter.getInstance().build("/login/loginActivity").navigation()
            }
            ctx.finish()
    }
}