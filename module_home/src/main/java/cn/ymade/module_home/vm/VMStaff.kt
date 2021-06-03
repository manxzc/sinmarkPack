package cn.ymade.module_home.vm

import android.util.Log
import androidx.annotation.MainThread
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import cn.ymade.module_home.adapter.DepartAdapter
import cn.ymade.module_home.adapter.StaffAdapter
import cn.ymade.module_home.db.beans.DepartBean
import cn.ymade.module_home.db.beans.DevInfoBean
import cn.ymade.module_home.db.beans.StaffBean
import cn.ymade.module_home.db.database.DataBaseManager
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
 * @date 2021/6/3 0003.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
class VMStaff :BaseViewModel() {

    val departList= mutableListOf<DepartBean>()
    val staffList= mutableListOf<StaffBean>()

    var selectDepart:DepartBean?=null

    var selectStaff:StaffBean?=null



    var departAdapter=DepartAdapter(departList,object :CallBack<DepartBean>{
        override fun callBack(data: DepartBean?) {
            getSelectStaffByDepart(data)

        }
    })

    private fun getSelectStaffByDepart(data: DepartBean?) {
        staffList.clear()
        staffAdapter.clearSelect()
        Log.i("CallBack ", "callBack: " + data.toString())
        data?.let {
            selectDepart = data
            Observable.create<List<StaffBean>> {
                val list = DataBaseManager.db.departStaffDao().getAllStaffByDepart(data.Depart)
                Log.i("TAG", "callBack:  list " + list.size)
                it.onNext(list)
            }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Observer<List<StaffBean>> {
                    override fun onSubscribe(d: Disposable?) {
                    }

                    override fun onNext(t: List<StaffBean>?) {
                        t?.let {
                            staffList.addAll(it)
                            staffAdapter.notifyDataSetChanged()
                        }

                    }

                    override fun onError(e: Throwable?) {
                        Log.i("TAG", "callBack onError: " + e?.message)
                        CommUtil.ToastU.showToast("加载人员失败~")

                    }

                    override fun onComplete() {
                    }

                })
        }
    }

    val staffAdapter=StaffAdapter(staffList,object :CallBack<StaffBean>{
        override fun callBack(data: StaffBean?) {
            data?.let {
                selectStaff=data
            }
        }
    })

    fun initStaff(staffRv:RecyclerView){
        staffRv.layoutManager=LinearLayoutManager(staffRv.context)
        staffRv.adapter=staffAdapter
    }
    fun initDepart(rv:RecyclerView){
        rv.layoutManager=LinearLayoutManager(rv.context)
        rv.adapter=departAdapter
        Observable.create<List<DepartBean>> {
            it.onNext(DataBaseManager.db.departStaffDao().getAllDepart())
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(object :Observer<List<DepartBean>>{
                override fun onSubscribe(d: Disposable?) {
                }

                override fun onNext(t: List<DepartBean>?) {
                    t?.let {
                        departList.addAll(it)

                    departAdapter.notifyDataSetChanged()
                        t.forEach {
                            if (it.current==1){
                                getSelectStaffByDepart(it)
                                return
                            }
                        }


                    }
                }

                override fun onError(e: Throwable?) {
                    CommUtil.ToastU.showToast("加载部门失败~")
                }

                override fun onComplete() {
                }

            })

    }
    fun commit(cbk:CallBack<StaffBean>){

      Observable.create<StaffBean> {
            selectStaff?.let { stf->
                    DataBaseManager.db.beginTransaction()
                    DataBaseManager.db.departStaffDao().updateClearCurrentDepart()
                    DataBaseManager.db.departStaffDao().updateClearCurrentstaff()
                Log.i("TAG", "commit: Depart "+selectDepart?.Depart)
                selectDepart?.Depart?.let { it1 -> DataBaseManager.db.departStaffDao().updateCurrentDepart(it1) }
                    stf.Staff?.let { it1 -> DataBaseManager.db.departStaffDao().updateCurrentStaff(it1) }
                    DataBaseManager.db.setTransactionSuccessful()
                    DataBaseManager.db.endTransaction()
                it.onNext(selectStaff)
            }



        }
            .subscribeOn(Schedulers.io())//切换线程
            .subscribe {
                AppConfig.staff.put(it.Staff)
                cbk.callBack(it)
            }
    }

}