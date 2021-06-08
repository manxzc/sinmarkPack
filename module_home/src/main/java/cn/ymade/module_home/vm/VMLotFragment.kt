package cn.ymade.module_home.vm

import android.content.Intent
import android.text.TextUtils
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.ymade.module_home.adapter.LotAdapter
import cn.ymade.module_home.db.beans.LotDataBean
import cn.ymade.module_home.db.database.DataBaseManager
import cn.ymade.module_home.ui.LotInfoActivity
import cn.ymade.module_home.ui.fragment.LotFragment
import com.zcxie.zc.model_comm.base.BaseViewModel
import com.zcxie.zc.model_comm.callbacks.CallBack
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
class VMLotFragment :BaseViewModel() {
    var fragment: LotFragment?=null
    val snList= mutableListOf<LotDataBean>()
    var lastSearch:String=""
    var type:Int=0


    var snAdapter= LotAdapter(snList,object : CallBack<LotDataBean> {
        override fun callBack(data: LotDataBean?) {
            Log.i(TAG, "VMListFragment callBack: data "+data.toString())
            fragment!!.context!!.startActivity(Intent( fragment!!.context!!, LotInfoActivity::class.java).putExtra("selectLot",data))
        }
    })

    fun initData(type:Int, rv: RecyclerView, fragment:LotFragment){
        this.fragment=fragment
        this.type=type
        rv.layoutManager= LinearLayoutManager(rv.context)
        rv.adapter=snAdapter
        doSearch(type, fragment)
    }
    private fun doSearch(type: Int, fragment: LotFragment) {
        snList.clear()
        Observable.create<List<LotDataBean>> {
            if (TextUtils.isEmpty(lastSearch)) {
                when (type) {
                    0 -> it.onNext(DataBaseManager.db.lotDao().getAll())
                    1 -> it.onNext(DataBaseManager.db.lotDao().getAllByLotUp(0))
                    2 -> it.onNext(DataBaseManager.db.lotDao().getAllByLotUp(1))
                }
            }
            else{
                when (type) {
                    0 -> it.onNext(DataBaseManager.db.lotDao().getAllByLotNo(lastSearch))
                    1 -> it.onNext(DataBaseManager.db.lotDao().getAllByLotUpAndNo(0,lastSearch))
                    2 -> it.onNext(DataBaseManager.db.lotDao().getAllByLotUpAndNo(1,lastSearch))
                }
            }

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<LotDataBean>> {
                override fun onSubscribe(d: Disposable?) {
                }

                override fun onNext(t: List<LotDataBean>?) {
                    Log.i(TAG, "onNext: initData " + t?.size)
                    snList.clear()
                    snList.addAll(t!!)
                    snAdapter.notifyDataSetChanged()

                    fragment.reloadTitle(getTabStr(type, t.size))
                    fragment.showNotData(t.size==0)
                }

                override fun onError(e: Throwable?) {
                }

                override fun onComplete() {
                }
            })
    }
    fun getTabStr(index: Int, searchCount: Int): String {
        Log.i("TAG", "getTabStr:  searchCount $searchCount")
        return if (index == 0) "全部($searchCount)" else if (index == 1) "未上传($searchCount)" else if (index == 2) "已上传($searchCount)"  else "全部($searchCount)"
    }

    fun searchData(string: String){

        if (string!=lastSearch){
            lastSearch=string
            doSearch(type,fragment!!)
        }

    }
    fun reSearchData(){
        Log.i(TAG, "reSearchData: ")
            doSearch(type,fragment!!)
    }
}