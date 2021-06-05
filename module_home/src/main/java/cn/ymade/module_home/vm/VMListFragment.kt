package cn.ymade.module_home.vm

import android.text.TextUtils
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.ymade.module_home.adapter.DepartAdapter
import cn.ymade.module_home.adapter.SNLIstAdapter
import cn.ymade.module_home.db.beans.DepartBean
import cn.ymade.module_home.db.beans.SNBean
import cn.ymade.module_home.db.beans.StaffBean
import cn.ymade.module_home.db.database.DataBaseManager
import cn.ymade.module_home.ui.fragment.SNListFragment
import com.zcxie.zc.model_comm.base.BaseViewModel
import com.zcxie.zc.model_comm.callbacks.CallBack
import com.zcxie.zc.model_comm.util.LiveDataBus
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
class VMListFragment :BaseViewModel() {

    var fragment:SNListFragment?=null
    val snList= mutableListOf<SNBean>()
    var lastSearch:String=""
    var type:Int=0


    var snAdapter= SNLIstAdapter(snList,object : CallBack<SNBean> {
        override fun callBack(data: SNBean?) {
            Log.i(TAG, "VMListFragment callBack: data "+data.toString())
        }
    })


    fun initData(type:Int,rv:RecyclerView,fragment:SNListFragment){
        this.fragment=fragment
        this.type=type
        rv.layoutManager= LinearLayoutManager(rv.context)
        rv.adapter=snAdapter
        doSearch(type, fragment)
    }

    private fun doSearch(type: Int, fragment: SNListFragment) {
        snList.clear()
        Observable.create<List<SNBean>> {
            if (TextUtils.isEmpty(lastSearch)) {
                when (type) {
                    0 -> it.onNext(DataBaseManager.db.snDao().getAllNotDelete())
                    1 -> it.onNext(DataBaseManager.db.snDao().getAllInOut(0))
                    2 -> it.onNext(DataBaseManager.db.snDao().getAllInOut(1))
                }
            }
            else{
                when (type) {
                    0 -> it.onNext(DataBaseManager.db.snDao().searchAllNotDeleteBy(lastSearch))
                    1 -> it.onNext(DataBaseManager.db.snDao().searchAllInOut(0,lastSearch))
                    2 -> it.onNext(DataBaseManager.db.snDao().searchAllInOut(1,lastSearch))
                }
            }

        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<SNBean>> {
                override fun onSubscribe(d: Disposable?) {
                }

                override fun onNext(t: List<SNBean>?) {
                    Log.i(TAG, "onNext: initData " + t?.size)
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


    fun searchData(string: String){

        if (string!=lastSearch){
            lastSearch=string
            doSearch(type,fragment!!)
        }

    }

    fun getTabStr(index: Int, searchCount: Int): String {
        Log.i("TAG", "getTabStr:  searchCount $searchCount")
        return if (index == 0) "全部($searchCount)" else if (index == 1) "在库($searchCount)" else if (index == 2) "已出库($searchCount)"  else "全部($searchCount)"
    }

}