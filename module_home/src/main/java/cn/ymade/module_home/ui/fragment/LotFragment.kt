package cn.ymade.module_home.ui.fragment

import android.view.View
import androidx.lifecycle.Observer
import cn.ymade.module_home.R
import cn.ymade.module_home.common.Constant
import cn.ymade.module_home.databinding.FragmentLotListBinding
import cn.ymade.module_home.ui.LotActivity
import cn.ymade.module_home.vm.VMLotFragment
import com.zcxie.zc.model_comm.base.BaseFragment
import com.zcxie.zc.model_comm.util.LiveDataBus

/**
 * @author zc.xie
 * @date 2021/6/6 0006.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
class LotFragment(val type: Int, val title: String) :BaseFragment<VMLotFragment, FragmentLotListBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.fragment_lot_list
    }

    override fun findViewModelClass(): Class<VMLotFragment> {
        return VMLotFragment::class.java
    }

    override fun initView() {

    }

    override fun initData() {
        mBinding?.let {

            mViewModel?.initData(type, it.assetsRv, this)

        }
    }

    override fun initEvent() {
        LiveDataBus.get().with("searchLot", String::class.java)
            .observe(this, { t -> mViewModel?.searchData(t) })
        LiveDataBus.get().with("notyChange", String::class.java)
            .observe(this, { mViewModel?.reSearchData() })
    }

    fun  reloadTitle(title: String){
        ( getActivity() as LotActivity).refreshTab(type, title)

    }
    fun showNotData(show: Boolean){
        if (show){
            mBinding!!.assetListEmpty.visibility= View.VISIBLE
        }else{
            mBinding!!.assetListEmpty.visibility= View.GONE
        }
    }

}