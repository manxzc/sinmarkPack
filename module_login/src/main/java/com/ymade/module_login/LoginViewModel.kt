package com.ymade.module_login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.ymade.module_login.net.LoginApi
import com.zcxie.zc.model_comm.base.BaseViewModel
import com.zcxie.zc.model_comm.model.BaseModel
import com.zcxie.zc.model_comm.net.RetrofitManager
import com.zcxie.zc.model_comm.util.AppConfig
import com.zcxie.zc.model_comm.util.CommUtil
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel :BaseViewModel() {
    fun registerAndLogin(company: String, devId: String): MutableLiveData<String> {
        val listMutableLiveData: MutableLiveData<String> = MutableLiveData<String>()
        RetrofitManager.retrofit
            .create(LoginApi::class.java)
            .register("cn.ymade.pack", company,devId,CommUtil.getIMEI(),CommUtil.getPhoneModel())
            .enqueue(object : Callback<LoginModel> {
            override fun onResponse(call: Call<LoginModel>, response: Response<LoginModel>) {
                Log.i("TAG", "registerAndLogin: code"+response.body()!!.code+" token "+response.body()!!.Token)
                if (response.body()!!.code==1){
                    AppConfig.Token.put(response.body()!!.Token)
                }
                listMutableLiveData.postValue(if (response.body()!!.code==1)"" else "-1")
            }

            override fun onFailure(call: Call<LoginModel>, t: Throwable) {
                Log.i(TAG, "onFailure: ")
                listMutableLiveData.postValue( "-1")
            }

        })



        return listMutableLiveData
    }
}