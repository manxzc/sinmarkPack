package cn.ymade.module_home.ui

import cn.ymade.module_home.R
import cn.ymade.module_home.databinding.ActivityLotBinding
import cn.ymade.module_home.vm.VMLot
import com.zcxie.zc.model_comm.base.BaseActivity
import com.zcxie.zc.model_comm.util.LiveDataBus

/**
 * @author zc.xie
 * @date 2021/6/6 0006.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
class LotActivity : BaseActivity<VMLot,ActivityLotBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_lot
    }

    override fun processLogic() {
        initTopSearchBar()
        mBinding?.let { binding->
            binding.mViewPager.adapter= mViewModel?.initFragment(this)
            binding.mViewPager.offscreenPageLimit=3
            binding.mTabLayout.setupWithViewPager(binding.mViewPager)
        }

    }

    override fun findViewModelClass(): Class<VMLot> {
        return VMLot::class.java
    }

    override fun onSearchAction(s: String) {
        super.onSearchAction(s)
        LiveDataBus.get().with("searchLot").postValue(s)
    }
    fun refreshTab(index: Int, count: String?) {
        mBinding?.mTabLayout!!.getTabAt(index)?.setText(count)
    }
}