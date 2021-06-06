package cn.ymade.module_home.vm

import android.util.Log
import cn.ymade.module_home.db.database.DataBaseManager
import cn.ymade.module_home.model.SummaryData
import com.zcxie.zc.model_comm.base.BaseViewModel
import com.zcxie.zc.model_comm.callbacks.CallBack
import com.zcxie.zc.model_comm.util.CommUtil
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.schedulers.Schedulers
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * @author zc.xie
 * @date 2021/6/4 0004.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
class VMSummary :BaseViewModel() {

fun getNum(starTime : String, stopTime : String, callback: CallBack<SummaryData>){
    val df2: DateFormat = SimpleDateFormat("yyyy-MM-dd")
    var d1: Date? = null
    var d2: Date? = null
    try {
        d1 = df2.parse(starTime)
        d2 = df2.parse(stopTime)
    } catch (e: ParseException) {
        e.printStackTrace()
    }
    val d3 = (d2!!.time)/1000 - (d1!!.time)/1000
    val d4 = 2592000

    val dateFormat: DateFormat = SimpleDateFormat("yyyy-MM-dd")
    val nowdayTime = dateFormat.format(Date())
    val nowDate = dateFormat.parse(nowdayTime)

    if ((d2!!.time)/1000 > (nowDate!!.time)/1000) {
        CommUtil.ToastU.showToast("查询时间不能超过今天")
        return
    }
    if (d3 > d4) {
        CommUtil.ToastU.showToast("查询时间不能超过30天")
        return
    }
    if (d3 < 0) {
        CommUtil.ToastU.showToast("结束时间不能小于开始时间")
        return
    }

    Observable.create<SummaryData> {
        val typeNum = DataBaseManager.db.snDao().getTitleNum(1) //所有数量
        val allNum= DataBaseManager.db.snDao().getTimeAll(1,starTime,stopTime) //类型
        var homeTitleData= SummaryData(allNum,typeNum)
        it.onNext(homeTitleData)
    }.subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe {
            callback.callBack(it)
        }

}
}