package cn.ymade.module_home.vm

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import cn.ymade.module_home.model.DeviceInfo
import cn.ymade.module_home.net.DeviceInfoApi
import com.google.gson.Gson
import com.zcxie.zc.model_comm.base.BaseViewModel
import com.zcxie.zc.model_comm.net.RetrofitManager
import com.zcxie.zc.model_comm.util.AppConfig
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author zc.xie
 * @date 2021/6/1 0001.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
class VMSetting :BaseViewModel() {


//    fun getUserInfo(): MutableLiveData<DeviceInfoData.DeviceBean?>? {
//        return userInfo
//    }
//
//    fun setUserInfo(userInfo: MutableLiveData<DeviceInfoData.DeviceBean?>) {
//        this.userInfo = userInfo
//    }
//
//    private var userInfo: MutableLiveData<DeviceInfoData.DeviceBean?> =
//        MutableLiveData<DeviceInfoData.DeviceBean?>()
//    fun initDeviceInfo(){
//        val deviceInfo:String=AppConfig.deviceInfo.get()
//        if (TextUtils.isEmpty(deviceInfo)){
//            getDeviceINfo()
//        }else{
//            userInfo.value= Gson().fromJson<DeviceBean::class>(deviceInfo)
//        }
//    }
fun getSerDeviceINfo(){
    RetrofitManager.retrofit
        .create(DeviceInfoApi::class.java)
        .queryDvInfo(AppConfig.Token.get())
        .enqueue(object : Callback<DeviceInfo> {
            override fun onResponse(call: Call<DeviceInfo>, response: Response<DeviceInfo>) {

                Log.i("TAG", "onResponse: ")
            }

            override fun onFailure(call: Call<DeviceInfo>, t: Throwable) {
                Log.i("TAG", "onFailure: ")
            }

        })
//        RetrofitManager.retrofit
//            .create(DeviceInfoApi::class.java)
//            .queryDvInfo(AppConfig.Token.get())
//            .subscribeOn(Schedulers.io())
//            .observeOn(Schedulers.io())
//            .subscribe {
//                object :io.reactivex.rxjava3.core.Observer<DeviceInfo>{
//                    override fun onSubscribe(d: Disposable?) {
//                        Log.i(TAG, "onSubscribe: ")
//                    }
//
//                    override fun onNext(t: DeviceInfo?) {
//                        Log.i(TAG, "onNext: ")
//                        t?.let {
//                            if (it.code == 1) {
//                                if (it.Device.size > 0) {
//                                    val dev: Device = it.Device[0]
//                                    val ver: Version = it.Version[0]
//                                    var dbBean = DevInfoBean(
//                                        dev.UUID,
//                                        dev.Company,
//                                        dev.CompanySN,
//                                        dev.Device,
//                                        dev.DeviceSN,
//                                        dev.ExpiryDate,
//                                        dev.RegDate,
//                                        dev.SN,
//                                        dev.UUID,
//                                        ver.Renew,
//                                        ver.Url,
//                                        ver.Version
//                                    )
//                                    DataBaseManager.db.devinfoDao().insertAll(dbBean)
//                                    Log.i(
//                                        TAG,
//                                        "getDeviceINfo: " + DataBaseManager.db.devinfoDao().getAll().size
//                                    )
//                                }
//
//                            }
//                        }
//                    }
//
//                    override fun onError(e: Throwable?) {
//                        Log.i(TAG, "onError: ")
//                    }
//
//                    override fun onComplete() {
//                        Log.i(TAG, "onComplete: ")
//                    }
//
//
//                }
//
//            }
}
}