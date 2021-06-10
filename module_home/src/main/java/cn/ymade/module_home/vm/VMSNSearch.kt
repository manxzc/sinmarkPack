package cn.ymade.module_home.vm

import android.text.TextUtils
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.ymade.module_home.adapter.SNListAdapter
import cn.ymade.module_home.db.beans.SNBean
import cn.ymade.module_home.db.database.DataBaseManager
import cn.ymade.module_home.ui.SNSearchActivity
import com.zcxie.zc.model_comm.base.BaseViewModel
import com.zcxie.zc.model_comm.callbacks.CallBack
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
class VMSNSearch: BaseViewModel() {

    val snList= mutableListOf<SNBean>()
    var lastSearch:String=""

    var act: SNSearchActivity?=null
    var snAdapter= SNListAdapter(snList,object : CallBack<SNBean> {
        override fun callBack(data: SNBean?) {
            Log.i(TAG, "VMListFragment callBack: data "+data.toString())
        }
    })

    fun initData(rv: RecyclerView, act: SNSearchActivity){
        this.act=act
        rv.layoutManager= LinearLayoutManager(rv.context)
        rv.adapter=snAdapter
    }

     fun doSearch( ) {
        snList.clear()
        Observable.create<List<SNBean>> {
                it.onNext(DataBaseManager.db.snDao().searchAllNotDeleteByMatchSN(lastSearch))

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<SNBean>> {
                override fun onSubscribe(d: Disposable?) {
                }
                override fun onNext(t: List<SNBean>?) {
                    Log.i(TAG, "onNext: initData " + t?.size)
                    snList.addAll(t!!)
                    snAdapter.notifyDataSetChanged()
                    act!!.reloadTitle(getTabStr( t.size))
                    act!!.showNotData(t.size==0)
                }

                override fun onError(e: Throwable?) {
                }

                override fun onComplete() {
                }
            })
    }
    fun searchData(key:String){
        if (!TextUtils.isEmpty(key)&&lastSearch!=key){
            lastSearch=key
            doSearch()
        }
    }
    fun getTabStr( searchCount: Int): String {
        Log.i("TAG", "getTabStr:  searchCount $searchCount")
        return  "总计:   ( $searchCount )  "
    }

}