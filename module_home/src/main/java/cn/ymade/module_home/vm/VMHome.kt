package cn.ymade.module_home.vm

import android.util.Log
import androidx.lifecycle.MutableLiveData
import cn.ymade.module_home.db.beans.DevInfoBean
import cn.ymade.module_home.db.database.DataBaseManager
import cn.ymade.module_home.db.database.DataBaseManager.db
import cn.ymade.module_home.model.*
import cn.ymade.module_home.net.DeviceInfoApi
import com.zcxie.zc.model_comm.base.BaseViewModel
import com.zcxie.zc.model_comm.callbacks.CallBack
import com.zcxie.zc.model_comm.net.RetrofitManager
import com.zcxie.zc.model_comm.util.AppConfig
import com.zcxie.zc.model_comm.util.CommUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class VMHome :BaseViewModel() {

    init {
        initDeviceInfo()
        checkStaffData()
    }

    private fun checkStaffData() {
        if (!AppConfig.hasStaff.get()) {
            RetrofitManager.retrofit
                .create(DeviceInfoApi::class.java)
                .queryDepartStaff(AppConfig.Token.get())
                .enqueue(object : Callback<DepartStaffInfo> {
                    override fun onResponse(
                        call: Call<DepartStaffInfo>,
                        response: Response<DepartStaffInfo>
                    ) {
                        if (response.isSuccessful) {
                            response.body()?.let {
                                if (it.code==1&& it.Depart.isNotEmpty()){
                                    AppConfig.hasStaff.put(true)
                                    Thread{
                                        DataBaseManager.db.departStaffDao().deleteAllDepart()
                                        DataBaseManager.db.departStaffDao().deleteAllStaff()
                                        DataBaseManager.db.departStaffDao().insertListDepart(it.Depart)
                                        if (it.Staff.isNotEmpty()) {
                                            DataBaseManager.db.departStaffDao().insertListStaff(it.Staff)
                                        }
                                    }.start()
                                }
                            }
                        }

                    }

                    override fun onFailure(call: Call<DepartStaffInfo>, t: Throwable) {
                        CommUtil.ToastU.showToast("员工获取失败")
                    }
                })
        }else{

        }

    }

    fun getUserInfo(): MutableLiveData<DevInfoBean?>? {
        return userInfo
    }
    //
    fun setUserInfo(userInfo: MutableLiveData<DevInfoBean?>) {
        this.userInfo = userInfo
    }

    private var userInfo: MutableLiveData<DevInfoBean?> =
            MutableLiveData<DevInfoBean?>()

    private fun initDeviceInfo(){
        getSerDeviceINfo()
    }

    fun getSerDeviceINfo(){
        if (AppConfig.hasDevInfo.get()){
            Observable.create<DevInfoBean> {
                it.onNext(DataBaseManager.db.devinfoDao().getAll()[0])
            }.subscribeOn(Schedulers.io())
                    .subscribe(object : Observer<DevInfoBean> {
                        override fun onNext(t: DevInfoBean?) {
                            t?.let {
                                Log.i(TAG, "onNext: "+t.toString())
                                userInfo.postValue(t)
                            }
                        }

                        override fun onError(e: Throwable?) {
                        }

                        override fun onSubscribe(d: Disposable?) {
                        }

                        override fun onComplete() {
                        }

                    })
        }else{
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
                                                dev.UUID,
                                                dev.Company,
                                                    dev.CompanySN,
                                                    dev.Device,
                                                    dev.DeviceSN,
                                                    dev.ExpiryDate,
                                                    dev.RegDate,
                                                    dev.SN,
                                                    ver.Renew,
                                                    ver.Url,
                                                    ver.Version
                                            )
                                            userInfo.postValue(dbBean)
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
    }

    fun getHomeTitleData( callback:CallBack<HomeTitleData>){
        Observable.create<HomeTitleData> {
            val inNum = db.snDao().getAllInOutContBy(0) //在库的
            val outNum=db.snDao().getAllOutContBy(CommUtil.getCurrentTimeYMD())
            val waitNum=db.snDao().getWaitContBy(1)
            var homeTitleData=HomeTitleData(inNum,outNum,waitNum)
            it.onNext(homeTitleData)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                Log.i("TAG", "loadData: getHomeTitleData " + it)
                callback.callBack(it)
            }

    }
}