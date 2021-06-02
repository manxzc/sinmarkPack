package com.ymade.module_login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ymade.module_login.net.LoginApi
import com.zcxie.zc.model_comm.base.BaseViewModel
import com.zcxie.zc.model_comm.net.RetrofitManager
import com.zcxie.zc.model_comm.util.AppConfig
import com.zcxie.zc.model_comm.util.CommUtil
import io.reactivex.rxjava3.schedulers.Schedulers

class LoginViewModel :BaseViewModel() {
    fun registerAndLogin(company: String, devId: String): MutableLiveData<String> {
        val listMutableLiveData: MutableLiveData<String> = MutableLiveData<String>()
        RetrofitManager.retrofit
            .create(LoginApi::class.java)
            .register("cn.ymade.pack",
                company,devId,CommUtil.getIMEI(),CommUtil.getPhoneModel())
            .subscribeOn(Schedulers.io())
            .subscribe {
                if (it.code==1){
                    AppConfig.Token.put(it.Token)
                }
                listMutableLiveData.postValue(if (it.code==1)"" else "-1")
                Log.i("TAG", "registerAndLogin: code"+it.code+" token "+it.Token)
            }


        return listMutableLiveData
    }
}