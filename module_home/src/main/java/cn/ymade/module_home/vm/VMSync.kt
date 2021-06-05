package cn.ymade.module_home.vm

import android.util.Log
import cn.ymade.module_home.common.Constant
import cn.ymade.module_home.db.database.DataBaseManager
import cn.ymade.module_home.model.GoodList
import cn.ymade.module_home.net.DeviceInfoApi
import cn.ymade.module_home.ui.SyncActvity
import com.zcxie.zc.model_comm.base.BaseViewModel
import com.zcxie.zc.model_comm.model.BaseModel
import com.zcxie.zc.model_comm.net.RetrofitManager
import com.zcxie.zc.model_comm.util.AppConfig
import com.zcxie.zc.model_comm.util.LiveDataBus
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author zc.xie
 * @date 2021/6/4 0004.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
class VMSync :BaseViewModel() {

    var NextSN=0;
    fun download(activity: SyncActvity){
        RetrofitManager.retrofit
            .create(DeviceInfoApi::class.java)
            .queryGoodList(AppConfig.Token.get(),NextSN)
            .enqueue(object :Callback<GoodList> {
                override fun onResponse(call: Call<GoodList>, response: Response<GoodList>) {
                    Log.i(TAG, "onResponse: "+response.body()?.Goods?.size)
                    if (response.isSuccessful&&response.body()!=null){
                        NextSN=response.body()!!.NextSN
                        Thread{
                         val localLis=  DataBaseManager.db.snDao().getAll();
                            Log.i(TAG, "onResponse: ")
                            for ( serBean in response.body()!!.Goods){
                                for (localBean in localLis){
                                    if (serBean.SN==localBean.SN){
                                        serBean.LotSN=localBean.LotSN
                                        serBean.Status=localBean.Status
                                        serBean.out=localBean.out
                                    }

                                }
                                DataBaseManager.db.snDao().insert(serBean)
                            }
                            if (NextSN==0){
                                activity.runOnUiThread(object :Runnable{
                                    override fun run() {
                                        activity.hideProgress()
                                        LiveDataBus.get().with(Constant.LD_UP_HOME_TITLE).postValue(1)
                                    }
                                })
                            }else{
                                download(activity)
                            }

                        }.start()

                    }
                }

                override fun onFailure(call: Call<GoodList>, t: Throwable) {
                    Log.i(TAG, "onFailure: "+t.message)
                    activity.hideProgress()
                }

            })
    }

    fun upload(activity: SyncActvity){
        RetrofitManager.retrofit
            .create(DeviceInfoApi::class.java)
            .queryUpload(AppConfig.Token.get())
            .enqueue(object :Callback<BaseModel> {
                override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {
                    activity.hideProgress()
                }

                override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                    activity.hideProgress()
                }

            })
    }
}