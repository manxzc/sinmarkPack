package cn.ymade.module_home.ui.fragment

import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import cn.ymade.module_home.R
import cn.ymade.module_home.databinding.FragmentSnlistBinding
import cn.ymade.module_home.ui.SNListActivity
import cn.ymade.module_home.vm.VMListFragment
import com.zcxie.zc.model_comm.base.BaseFragment
import com.zcxie.zc.model_comm.util.LiveDataBus

/**
 * @author zc.xie
 * @date 2021/6/5 0005.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
class SNListFragment(val type: Int,val title:String):BaseFragment<VMListFragment,FragmentSnlistBinding>() {

    override fun getLayoutId(): Int {
        return R.layout.fragment_snlist
    }

    override fun findViewModelClass(): Class<VMListFragment> {
        return VMListFragment::class.java
    }

    override fun initView() {

    }

    override fun initData() {
       mBinding?.let {

           mViewModel?.initData(type,it.assetsRv,this)

       }
    }

    override fun initEvent() {
        LiveDataBus.get().with("searchSN", String::class.java).observe(this,object :Observer<String>{
            override fun onChanged(t: String) {
                mViewModel?.searchData(t)
            }

        })
    }
    fun  reloadTitle(title: String){
        ( getActivity() as SNListActivity).refreshTab(type, title)

    }
    fun showNotData(show:Boolean){
        if (show){
           mBinding!!.assetListEmpty.visibility= View.VISIBLE
        }else{
            mBinding!!.assetListEmpty.visibility= View.GONE
        }
    }

}