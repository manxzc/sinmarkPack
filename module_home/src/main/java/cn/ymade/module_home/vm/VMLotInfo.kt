package cn.ymade.module_home.vm

import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.ymade.module_home.adapter.LotInfoSnAdapter
import cn.ymade.module_home.common.Constant
import cn.ymade.module_home.db.beans.LotDataBean
import cn.ymade.module_home.db.beans.SNBean
import cn.ymade.module_home.db.beans.SNDeleteByLotBean
import cn.ymade.module_home.db.database.DataBaseManager
import cn.ymade.module_home.model.UploadLotbean
import cn.ymade.module_home.net.DeviceInfoApi
import cn.ymade.module_home.ui.LotInfoActivity
import com.zcxie.zc.model_comm.base.BaseViewModel
import com.zcxie.zc.model_comm.callbacks.CallBack
import com.zcxie.zc.model_comm.model.BaseModel
import com.zcxie.zc.model_comm.net.RetrofitManager
import com.zcxie.zc.model_comm.util.AppConfig
import com.zcxie.zc.model_comm.util.CommUtil
import com.zcxie.zc.model_comm.util.LiveDataBus
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * @author zc.xie
 * @date 2021/6/6 0006.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
class VMLotInfo:BaseViewModel() {
    var act:LotInfoActivity?=null
    var rv:RecyclerView?=null
    var lot:LotDataBean?=null
    var lotSN:String=""
    val outsideLis=mutableListOf<String>()
    val snList= mutableListOf<SNBean>()
    var lotSnInfoAdapter= LotInfoSnAdapter(snList,object : CallBack<SNBean> {
        override fun callBack(data: SNBean) {
            Log.i(TAG, " callBack: data "+data.toString())
            Thread{
                if (data.upload==2) { //如果上传过 需要存储 删除的数据
                    val deletelis = DataBaseManager.db.sndeleteSnDao()
                        .searchNotDeleteBySnAndLotSN(data!!.SN, data!!.LotSN)
                    if (deletelis.isNullOrEmpty()) {
                        var sndeleteBean = SNDeleteByLotBean()
                        sndeleteBean.LotSN = data!!.LotSN
                        sndeleteBean.SN = data!!.SN
                        sndeleteBean.ModifyTime = CommUtil.getCurrentTimeYMD()
                        DataBaseManager.db.sndeleteSnDao().insert(sndeleteBean)
                    } else {
                        deletelis[0].ModifyTime = CommUtil.getCurrentTimeYMD()
                        DataBaseManager.db.sndeleteSnDao().insert(deletelis[0])
                    }
                }
                data.LotSN=""
                data.ModifyTime=CommUtil.getCurrentTimeYMD()
                data.out=0
                data.upload=0
                DataBaseManager.db.snDao().updateSN(data)

            }.start()
            outsideLis.remove(data.SN)
            snList.remove(data)
            notyChange()
        }
    })
    var titleDifList= mutableSetOf<String>()
    fun notyChange(){
        titleDifList.clear()
        lot!!.upload=0
        this.lot!!.Stamp=System.currentTimeMillis()
        this.lot!!.items=snList.size
        Thread{
            DataBaseManager.db.lotDao().update(this.lot!!)
        }.start()
        lotSnInfoAdapter.notifyDataSetChanged()
        LiveDataBus.get().with(Constant.LD_UP_HOME_TITLE).postValue(1)
        LiveDataBus.get().with("deleteLotOrSn").postValue("1")
        for (sb in snList){
            titleDifList.add(sb.Title)
        }
        act!!.refresh(titleDifList.size.toString())
    }

    fun upLoadLotInfo(){
        act!!.showProgress("上传中")
        Thread{
            Log.i(TAG, "upLoadLot: lotSN $lotSN")
            if (lotSN.isNullOrEmpty()){
                Log.i(TAG, "upLoadLot: lotSN isNullOrEmpty ")
                return@Thread
            }
            val snList= DataBaseManager.db.snDao().getAllByLotSNAndUpcode(lotSN,1)
            val deleteList= DataBaseManager.db.sndeleteSnDao().getAllByLotSN(lotSN)
            var updb= UploadLotbean()
            updb.LotName=lot!!.LotName
            updb.LotNo=lot!!.LotNo
            updb.Stamp=lot!!.Stamp.toString()
            if (snList.isNotEmpty()) {
                if (snList.size <= 500) {
                    upComm(snList, lot!!, updb, true, true)
                } else {
                    var splitList = CommUtil.splitList(snList, 500)
                    for (list in splitList) {
                        if (list == splitList.get(splitList.size - 1)) {
                            upComm(list, lot!!, updb, true, true)
                        } else
                            upComm(list, lot!!, updb, false, true)
                    }
                }
            }
            if(deleteList.isNotEmpty()) {
                if (deleteList.size <= 500) {
                    upDelete(deleteList, lot!!, updb, true, true)
                } else {
                    var splitList = CommUtil.splitList(deleteList, 500)
                    for (list in splitList) {
                        if (list == splitList.get(splitList.size - 1)) {
                            upDelete(list, lot!!, updb, true, true)
                        } else
                            upDelete(list, lot!!, updb, false, true)
                    }
                }
            }
            if (snList.isNullOrEmpty()&&deleteList.isNullOrEmpty()){
                upComm(snList, lot!!, updb, true, true)
            }

        }.start()
    }

    private fun upComm(
            snList: List<SNBean>,
            lot: LotDataBean,
            updb: UploadLotbean,isEnd:Boolean,hidePro:Boolean
    ) {
        var jar= JSONArray()
        for (i in snList) {
            var jso= JSONObject();
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
        var jar= JSONArray()
        for (i in deleteList) {
            var jso= JSONObject();
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
                        ,upbd.LotNo,upbd.LotName,upbd.Stamp,upbd.sS!!)
                .enqueue(object : Callback<BaseModel> {
                    override fun onResponse(call: Call<BaseModel>, response: Response<BaseModel>) {

                        if (hidePro){
                            act!!.hideProgress()
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
                            act!!.hideProgress()
                        }
                        CommUtil.ToastU.showToast("失败！")
                    }

                })
    }


    fun deletLot(){
        act!!.showProgress("删除中")
        Observable.create<String> {
            for ( sb in snList ){
                sb!!.ModifyTime=CommUtil.getCurrentTimeYMD()
                sb.upload=0
                sb.out=0
                DataBaseManager.db.snDao().insert(sb)
            }
            this.lot!!.Status=0  //删除条目  暂定删除子条目 单据不为删除状态 删除单据才是
            it.onNext("success")

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String> {
                override fun onSubscribe(d: Disposable?) {
                }
                override fun onNext(t: String) {
                    snList.clear()
                    notyChange()
                    act!!.finish()
                }
                override fun onError(e: Throwable?) {
                }
                override fun onComplete() {
                }
            })
    }

    fun showDelete(show:Boolean){
        lotSnInfoAdapter.showDelete(show)
        lotSnInfoAdapter.notifyDataSetChanged()
    }
    fun initLotSn(lot: LotDataBean, rv: RecyclerView, act: LotInfoActivity){
        this.lot=lot
        this.lotSN=lot.LotSN
        this.act=act
        this.rv=rv
        rv.layoutManager= LinearLayoutManager(rv.context)
        lotSnInfoAdapter.showDelete=false
        rv.adapter=lotSnInfoAdapter
        getInitData()
    }

    private fun getInitData() {
        act?.showProgress("加载中")
        Observable.create<List<SNBean>> {
            it.onNext(DataBaseManager.db.snDao().getAllByLotSN(lot!!.LotSN))

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<SNBean>> {
                override fun onSubscribe(d: Disposable?) {
                }
                override fun onNext(t: List<SNBean>) {
                    Log.i(TAG, "onNext: initData " + t?.size)
                    for (sb in t){
                        outsideLis.add(sb.SN)
                        titleDifList.add(sb.Title)
                    }
                    snList.addAll(t!!)
                    lotSnInfoAdapter.notifyDataSetChanged()
                    act!!.hideProgress()
                    act!!.refresh(titleDifList.size.toString())
                }
                override fun onError(e: Throwable?) {
                }
                override fun onComplete() {
                }
            })
    }

    fun addScan(scanCode:String){

        outsideLis.forEach {
            if (it==scanCode)
            {
                CommUtil.ToastU.showToast("此条码已添加过~！")
                return
            }
        }
        outsideLis.add(scanCode)

        Log.i(TAG, "addScan: ")
        Observable.create<List<SNBean>> {

         DataBaseManager.db. sndeleteSnDao().deleteList(DataBaseManager.db. sndeleteSnDao().searchNotDeleteBySnAndLotSN(scanCode,lot!!.LotSN))


            it.onNext(DataBaseManager.db.snDao().searchsingleNotDeleteBySn(scanCode))

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<SNBean>> {
                override fun onSubscribe(d: Disposable?) {
                }
                override fun onNext(t: List<SNBean>) {
                    Log.i(TAG, "onNext: initData " + t?.size)
                    if (t.isEmpty()){
                        var sb=SNBean ()
                        sb.SN=scanCode
                        sb.ModifyTime=CommUtil.getCurrentTimeYMD()
                        sb.Title="--"
                        sb.LotSN=lot!!.LotSN
                        sb.out=1
                        sb.isLocal=1
                        sb.upload=1
                        snList.add(sb)
                        Thread{ DataBaseManager.db.snDao().insert(sb)}.start()
                    }else if (t[0].out!=0){
                        CommUtil.ToastU.showToast("此条码已被出库")
                        return
                    }else {
                        Thread{
                            for ( sb in t){
                                Log.i(TAG, "addScan: sb "+sb.Title)
                                sb.LotSN=lot!!.LotSN
                                sb.ModifyTime=CommUtil.getCurrentTimeYMD()
                                sb.out=1
                                sb.upload=1
                            }
                            DataBaseManager.db.snDao().insertList(t)
                        }.start()

                        snList.addAll(t!!)
                    }

                    notyChange()
                }
                override fun onError(e: Throwable?) {
                }
                override fun onComplete() {
                }
            })
    }

}