package cn.ymade.module_home.ui

import android.content.Intent
import android.util.Log
import cn.ymade.module_home.R
import cn.ymade.module_home.databinding.ActivityHomBinding
import cn.ymade.module_home.db.beans.DevInfoBean
import cn.ymade.module_home.db.database.DataBaseManager
import cn.ymade.module_home.model.Device
import cn.ymade.module_home.model.DeviceInfo
import cn.ymade.module_home.model.Version
import cn.ymade.module_home.net.DeviceInfoApi
import com.alibaba.android.arouter.facade.annotation.Route
import cn.ymade.module_home.vm.VMHome
import com.zcxie.zc.model_comm.base.BaseActivity
import com.zcxie.zc.model_comm.net.RetrofitManager
import com.zcxie.zc.model_comm.util.AppConfig
import com.zcxie.zc.model_comm.util.CommUtil
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.cancel
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Route(path = "/home/homeActivity")
class HomeActivity :BaseActivity<VMHome, ActivityHomBinding>(), CoroutineScope by MainScope() {
    override fun getLayoutId(): Int {
        return R.layout.activity_hom
    }
    override fun processLogic() {
        getDevInfo()
    }

    override fun findViewModelClass(): Class<VMHome> {
        return VMHome::class.java
    }
    fun getDevInfo(){

        if (AppConfig.hasDevInfo.get()){
            //测试代码 跳转设备信息界面
            startActivity(Intent(this,SettingsActivity::class.java))
            return
        }
        RetrofitManager.retrofit
            .create(DeviceInfoApi::class.java)
            .queryDvInfo(AppConfig.Token.get())
            .enqueue(object : Callback<DeviceInfo> {
                override fun onResponse(call: Call<DeviceInfo>, response: Response<DeviceInfo>) {
                    if (response.isSuccessful) {
                        response.body()?.let {
                            if (it.code == 1) {
                                if (it.Device.size > 0) {
                                    val dev: Device = it.Device[0]
                                    val ver: Version = it.Version[0]
                                    var dbBean = DevInfoBean(
                                        dev.UUID,
                                        dev.Company,
                                        dev.CompanySN,
                                        dev.Device,
                                        dev.DeviceSN,
                                        dev.ExpiryDate,
                                        dev.RegDate,
                                        dev.SN,
                                        dev.UUID,
                                        ver.Renew,
                                        ver.Url,
                                        ver.Version
                                    )
                                    Thread {
                                        DataBaseManager.db.devinfoDao().insertAll(dbBean)
                                        AppConfig.hasDevInfo.put(true)
                                        Log.i(
                                            TAG,
                                            "getDeviceINfo: " + DataBaseManager.db.devinfoDao()
                                                .getAll().size
                                        )
                                    }.start()

                                }
                            }

                        }
                    }

                    Log.i(TAG, "onResponse:getDeviceINfo  isSuccessful "+response.isSuccessful+" company "+ response.body()?.Device?.get(0)?.Company)
                }

                override fun onFailure(call: Call<DeviceInfo>, t: Throwable) {
                    Log.i(TAG, "getDeviceINfo onFailure: ")
                    CommUtil.ToastU.showToast("获取设备信息失败")
                }

            })
    }

    override fun onDestroy() {
        super.onDestroy()
        cancel()
    }
}