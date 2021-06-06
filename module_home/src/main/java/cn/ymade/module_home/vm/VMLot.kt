package cn.ymade.module_home.vm

import androidx.fragment.app.FragmentActivity
import cn.ymade.module_home.adapter.LotListPageAdapter
import cn.ymade.module_home.adapter.SNListPageAdapter
import cn.ymade.module_home.ui.fragment.LotFragment
import cn.ymade.module_home.ui.fragment.SNListFragment
import com.zcxie.zc.model_comm.base.BaseViewModel

/**
 * @author zc.xie
 * @date 2021/6/6 0006.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
class VMLot :BaseViewModel() {

    fun initFragment(fragmentActivity: FragmentActivity): LotListPageAdapter {
        val listFragments= listOf(
            LotFragment(0,"全部"),
            LotFragment(1,"未上传"),
            LotFragment(2,"已上传")
        )
        return LotListPageAdapter(fragmentActivity.supportFragmentManager,listFragments)
    }
}