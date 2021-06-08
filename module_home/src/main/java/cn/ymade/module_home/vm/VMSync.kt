package cn.ymade.module_home.vm

import android.util.Log
import cn.ymade.module_home.common.Constant
import cn.ymade.module_home.db.beans.LotDataBean
import cn.ymade.module_home.db.beans.SNBean
import cn.ymade.module_home.db.beans.SNDeleteByLotBean
import cn.ymade.module_home.db.database.DataBaseManager
import cn.ymade.module_home.model.GoodList
import cn.ymade.module_home.model.UploadLotbean
import cn.ymade.module_home.model.UploadSNBean
import cn.ymade.module_home.net.DeviceInfoApi
import cn.ymade.module_home.ui.SyncActvity
import com.zcxie.zc.model_comm.base.BaseViewModel
import com.zcxie.zc.model_comm.model.BaseModel
import com.zcxie.zc.model_comm.net.RetrofitManager
import com.zcxie.zc.model_comm.util.AppConfig
import com.zcxie.zc.model_comm.util.CommUtil
import com.zcxie.zc.model_comm.util.LiveDataBus
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
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
    var activity:SyncActvity?=null
    fun initAct(activity: SyncActvity){
        this.activity=activity
    }

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

    fun upload(){
        activity!!.showProgress("正在上传中.")
        Thread{
         var listLot=   DataBaseManager.db.lotDao().getAllByLotNotUp()
            Log.i(TAG, "upload: listLot size "+listLot.size)
            if (listLot.isNullOrEmpty()){
                activity!!.runOnUiThread(object :Runnable{
                    override fun run() {
                        CommUtil.ToastU.showToast("货品已全部上传完毕")
                        activity!!.hideProgress()
                    }
                })
                return@Thread
            }
            var hasUp=false //防止删除未同步的数据
            for ( lot in listLot){
                hasUp=false
                val snList= DataBaseManager.db.snDao().getAllByLotSN(lot.LotSN)
                val deleteList= DataBaseManager.db.sndeleteSnDao().getAllByLotSN(lot.LotSN)
                var updb=UploadLotbean()
                updb.LotName=lot.LotName
                updb.LotNo=lot.LotNo
                updb.Stamp=lot.Stamp.toString()
                var hidePro= lot==listLot.get(listLot.size-1)
                if (snList.size>0) {
                    hasUp=true
                    if (snList.size <= 500) {
                        upComm(snList, lot, updb, true, hidePro)
                    } else {
                        var splitList = CommUtil.splitList(snList, 500)
//                        Log.i(TAG, "upload: 切割 splitList size  " + splitList.size)
                        for (list in splitList) {
                            if (list == splitList.get(splitList.size - 1)) {
                                upComm(list, lot, updb, true, hidePro)
                            } else
                                upComm(list, lot, updb, false, hidePro)
                        }
                    }
                }
                if(deleteList.size>0) {
                    hasUp=true
                    if (deleteList.size <= 500) {
                        upDelete(deleteList, lot, updb, true, hidePro)
                    } else {
                        var splitList = CommUtil.splitList(deleteList, 500)
//                        Log.i(TAG, "upload: 切割 splitList size  " + splitList.size)
                        for (list in splitList) {
                            if (list == splitList.get(splitList.size - 1)) {
                                upDelete(list, lot, updb, true, hidePro)
                            } else
                                upDelete(list, lot, updb, false, hidePro)
                        }
                    }
                }
                if (!hasUp){  //未同步的空单 也上传
                    upComm(snList, lot, updb, true, hidePro)
                }

            }

        }.start()
    }

    private fun upComm(
        snList: List<SNBean>,
        lot: LotDataBean,
        updb: UploadLotbean,isEnd:Boolean,hidePro:Boolean
    ) {
        var jar=JSONArray()
        for (i in snList) {
            var jso=JSONObject();
            jso.put("SN",i.SN)
            jso.put("Status",i.Status)
            jar.put(jso)
        }
        Log.i(TAG, "upload:  lot " + lot.LotSN + " " + lot.LotNo + " " + lot.LotName + " " + lot.Stamp + " " + lot.Status + " jsa "+jar.toString())
        updb.sS=jar
        uploadLot(lot,updb,snList,null,isEnd,hidePro)
    }

    private fun upDelete(
        deleteList: List<SNDeleteByLotBean>,
        lot: LotDataBean,
        updb: UploadLotbean,isEnd:Boolean,hidePro:Boolean
    ) {
        var jar=JSONArray()
        for (i in deleteList) {
                var jso=JSONObject();
                jso.put("SN",i.SN)
                jso.put("Status",i.Status)
                jar.put(jso)
        }
        Log.i(TAG, "upload:  deleteList lot " + lot.LotSN + " " + lot.LotNo + " " + lot.LotName + " " + lot.Stamp + " " + lot.Status + " ")
        updb.sS=jar
        uploadLot(lot,updb,null,deleteList,isEnd,hidePro)
    }

    fun uploadLot(  lot: LotDataBean,upbd: UploadLotbean,snList: List<SNBean>?, deleteList: List<SNDeleteByLotBean>?,isEnd:Boolean,hidePro:Boolean){
        RetrofitManager.retrofit
            .create(DeviceInfoApi::class.java)
            .queryUpload(AppConfig.Token.get()
                ,upbd.LotNo,upbd.LotName,lot.staff+"",upbd.Stamp,upbd.sS!!)
            .enqueue(object :Callback<BaseModel> {
                override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {

                    if (hidePro){
                        activity!!.hideProgress()
                    }
                    Log.i(TAG, " uploadLot onResponse: "+response.toString())
                    if (response.isSuccessful&&response.body()!=null){
                        if (response.body()!!.code==1){
                            LiveDataBus.get().with(Constant.LD_UP_HOME_TITLE).postValue(1)
                            if (!snList.isNullOrEmpty()){
                                Thread{
                                    for ( sb in snList){
                                        sb.upload=2
                                        DataBaseManager.db.snDao().insert(sb)
                                    }

                                }.start()
                            }
                            if (isEnd){
                                Thread{
                                   lot.upload=1;
                                    DataBaseManager.db.lotDao().insert(lot)

                                }.start()
                            }

                            if (!deleteList.isNullOrEmpty()){  //删除 本地数据库
                                Thread{
                                        DataBaseManager.db.sndeleteSnDao().deleteList(deleteList)

                                }.start()
                            }


                        }else{
                            if (response.body()!!.code==-99){
                                CommUtil.ToastU.showToast("操作失败")
                            }else{
                                CommUtil.ToastU.showToast(response.body()!!.msg)
                            }
                        }
                    }else{
                        CommUtil.ToastU.showToast("操作失败")
                    }
                }

                override fun onFailure(call: Call<BaseModel>, t: Throwable) {
                    Log.i(TAG, "uploadLot onFailure: "+t.toString())
                    if (hidePro){
                        activity!!.hideProgress()
                    }
                    CommUtil.ToastU.showToast("失败！")
                }

            })
    }
}