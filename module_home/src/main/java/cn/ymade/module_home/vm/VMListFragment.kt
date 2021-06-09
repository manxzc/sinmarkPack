package cn.ymade.module_home.vm

import android.content.Intent
import android.text.TextUtils
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.ymade.module_home.adapter.DepartAdapter
import cn.ymade.module_home.adapter.SNLIstAdapter
import cn.ymade.module_home.adapter.SnTitleAdapter
import cn.ymade.module_home.db.beans.DepartBean
import cn.ymade.module_home.db.beans.SNBean
import cn.ymade.module_home.db.beans.StaffBean
import cn.ymade.module_home.db.database.DataBaseManager
import cn.ymade.module_home.model.SNTitleBean
import cn.ymade.module_home.ui.LotInfoActivity
import cn.ymade.module_home.ui.NewSimpSNActivity
import cn.ymade.module_home.ui.SimpSNActivity
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
    val snList= mutableListOf<SNTitleBean>()
    var lastSearch:String=""
    var type:Int=0


    var snAdapter= SnTitleAdapter(snList,object : CallBack<SNTitleBean> {
        override fun callBack(data: SNTitleBean?) {
            Log.i(TAG, "VMListFragment callBack: data "+data.toString())
            fragment!!.context!!.startActivity(Intent( fragment!!.context!!, NewSimpSNActivity::class.java).putExtra("selectLot",data))
        }
    })


    fun initData(type:Int,rv:RecyclerView,fragment:SNListFragment){
        this.fragment=fragment
        this.type=type
        rv.layoutManager= LinearLayoutManager(rv.context)
        snAdapter.showDelete=false
        rv.adapter=snAdapter
        doSearch(type, fragment)
    }

    private fun doSearch(type: Int, fragment: SNListFragment) {
        snList.clear()
        Observable.create<List<SNTitleBean>> {
            if (TextUtils.isEmpty(lastSearch)) {
                var titleList = DataBaseManager.db.snDao().getAllDifTitleNotDelete()
                for (title in titleList) {
                    var stb=  SNTitleBean ()
                    var snLis= mutableListOf<SNBean>()
                    when (type) {
                        0 -> { snLis.addAll(DataBaseManager.db.snDao().getAllNotDeleteByTitle(title)) }
                        1 -> { snLis.addAll( DataBaseManager.db.snDao().getAllInOut(title,0)) }
                        2 -> { snLis.addAll(DataBaseManager.db.snDao().getAllInOut(title,1)) }
                    }
                    if (!snLis.isNullOrEmpty()) {
                        stb.count = snLis.size
                        stb.title = title
                        stb.snBeans = snLis
                        snList.add(stb)
                    }
                }
            }
            else{
                var stb=  SNTitleBean ()
                var snLis= mutableListOf<SNBean>()
                var titles=DataBaseManager.db.snDao().getAllDifTitleNotDeleteByTitle(lastSearch)
                for (title in titles) {
                    when (type) {
                        0 -> { snLis.addAll(DataBaseManager.db.snDao().getAllNotDeleteByTitle(title)) }
                        1 -> { snLis.addAll( DataBaseManager.db.snDao().searchAllInOut(0,title)) }
                        2 -> { snLis.addAll(DataBaseManager.db.snDao().searchAllInOut(1,title)) }
                    }
                    if (!snLis.isNullOrEmpty()) {
                        stb.count = snLis.size
                        stb.title = title
                        stb.snBeans = snLis
                        snList.add(stb)
                    }
                }
            }
            it.onNext(snList)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : Observer<List<SNTitleBean>> {
                override fun onSubscribe(d: Disposable?) {
                }

                override fun onNext(t: List<SNTitleBean>) {
                    Log.i(TAG, "onNext: initData " + t?.size)
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