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
import cn.ymade.module_home.ui.LotInfoActivity
import com.zcxie.zc.model_comm.base.BaseViewModel
import com.zcxie.zc.model_comm.callbacks.CallBack
import com.zcxie.zc.model_comm.util.CommUtil
import com.zcxie.zc.model_comm.util.LiveDataBus
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

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
    fun notyChange(){
        this.lot!!.upload=0
        this.lot!!.Stamp=System.currentTimeMillis()
        this.lot!!.items=snList.size
        Thread{
            DataBaseManager.db.lotDao().update(this.lot!!)

        }.start()
        lotSnInfoAdapter.notifyDataSetChanged()
        LiveDataBus.get().with(Constant.LD_UP_HOME_TITLE).postValue(1)
        LiveDataBus.get().with("deleteLotOrSn").postValue("1")

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
                    }
                    snList.addAll(t!!)
                    lotSnInfoAdapter.notifyDataSetChanged()
                    act!!.hideProgress()

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

//
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

                        snList.add(sb)
                        Thread{ DataBaseManager.db.snDao().insert(sb)}.start()
                    }else if (t[0].out!=0){
                        CommUtil.ToastU.showToast("此条码已被出库")
                        return
                    }else
                        snList.addAll(t!!)

                    notyChange()
                }
                override fun onError(e: Throwable?) {
                }
                override fun onComplete() {
                }
            })
    }

}