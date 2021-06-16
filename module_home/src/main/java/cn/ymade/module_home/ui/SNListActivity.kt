package cn.ymade.module_home.ui

import android.util.Log
import androidx.lifecycle.Observer
import cn.ymade.module_home.R
import cn.ymade.module_home.databinding.ActivitySnListBinding
import cn.ymade.module_home.homebase.ScanBaseActivity
import cn.ymade.module_home.vm.VMSNList
import com.zcxie.zc.model_comm.base.BaseActivity
import com.zcxie.zc.model_comm.util.LiveDataBus

/**
 * @author zc.xie
 * @date 2021/6/5 0005.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
//货品
class SNListActivity :ScanBaseActivity<VMSNList,ActivitySnListBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_sn_list
    }

    override fun processLogic() {
       mBinding?.let { binding->
           binding.mViewPager.adapter= mViewModel?.initFragment(this)
           binding.mViewPager.offscreenPageLimit=3
           binding.mTabLayout.setupWithViewPager(binding.mViewPager)
       }
        initTopSearchBar()
        initEvent()
    }

    override fun onSearchAction(s: String) {
        super.onSearchAction(s)
        LiveDataBus.get().with("searchSN").postValue(s)
    }



    private fun initEvent() {
    }

    override fun findViewModelClass(): Class<VMSNList> {
        return VMSNList::class.java
    }
    fun refreshTab(index: Int, count: String?) {
        mBinding?.mTabLayout!!.getTabAt(index)?.setText(count)
    }
    override fun loadCoded(scanCode: String) {
        setDoSeach(scanCode)
    }

    override fun enableFastSuccess(): Boolean {
        return true
    }
}