package cn.ymade.module_home.vm

import android.text.TextUtils
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.ymade.module_home.adapter.LotInfoSnAdapter
import cn.ymade.module_home.db.beans.LotDataBean
import cn.ymade.module_home.db.beans.SNBean
import cn.ymade.module_home.db.database.DataBaseManager
import cn.ymade.module_home.ui.CreateOutLotActivity
import com.zcxie.zc.model_comm.base.BaseViewModel
import com.zcxie.zc.model_comm.callbacks.CallBack
import com.zcxie.zc.model_comm.util.AppConfig
import com.zcxie.zc.model_comm.util.CommUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * @author zc.xie
 * @date 2021/6/5 0005.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
class VMCraeteOutLot :BaseViewModel() {

    var act:CreateOutLotActivity?=null
    var rv:RecyclerView?=null
    val outsideLis=mutableListOf<String>()
    val snList= mutableListOf<SNBean>()

    val LotSN= 10000+ AppConfig.lotAutoSN.get()

    var lotSnInfoAdapter= LotInfoSnAdapter(snList,object : CallBack<SNBean> {
        override fun callBack(data: SNBean?) {
            Log.i(TAG, " callBack: data "+data.toString())
           snList.remove(data)
           noty()
        }
    })
    fun noty(){
        lotSnInfoAdapter.notifyDataSetChanged()
    }

    var outLot=LotDataBean(LotSN.toString(),"","",System.currentTimeMillis(),1,0)
    fun init(rv:RecyclerView,act:CreateOutLotActivity){
        this.act=act
        this.rv=rv
        rv.layoutManager= LinearLayoutManager(rv.context)
        rv.adapter=lotSnInfoAdapter

    }
    var isCommitting=false
    fun commit(no:String,name:String,callBack: CallBack<String>){
        Log.i(TAG, "commit: $LotSN")
        if (isCommitting){
            CommUtil.ToastU.showToast("操作进行中,请勿重复点击!!")
            return
        }
        isCommitting=true

        Observable.create<String> {
            for (sb in snList){
                sb.LotSN= LotSN.toString()
                sb.upload=1
                sb.ModifyTime=CommUtil.getCurrentTimeYMD()
                sb.out=1
            }
            DataBaseManager.db.snDao().insertList(snList)
            outLot.Stamp=System.currentTimeMillis()
            outLot.LotName=name
            outLot.LotNo=no
            DataBaseManager.db.lotDao().insert(outLot)
            it.onNext("SUCCESS")
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<String>{
                override fun onSubscribe(d: Disposable?) {
                }

                override fun onNext(t: String) {
                    AppConfig.lotAutoSN.put(LotSN-9999)
                    isCommitting=false
                    callBack.callBack(t)
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
                            sb.LotSN=LotSN.toString()
                            sb.out=1

                        snList.add(sb)
                    }else if (t[0].upload!=0){
                        CommUtil.ToastU.showToast("此条码已被出库")
                        return
                    }else
                    snList.addAll(t!!)

                    lotSnInfoAdapter.notifyDataSetChanged()
                }
                override fun onError(e: Throwable?) {
                }
                override fun onComplete() {
                }
            })
    }

}