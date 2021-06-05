package cn.ymade.module_home.vm

import androidx.fragment.app.FragmentActivity
import cn.ymade.module_home.adapter.SNListPageAdapter
import cn.ymade.module_home.ui.fragment.SNListFragment
import com.zcxie.zc.model_comm.base.BaseViewModel
import java.util.*

/**
 * @author zc.xie
 * @date 2021/6/5 0005.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
class VMSNList :BaseViewModel(){

    fun initFragment(fragmentActivity: FragmentActivity):SNListPageAdapter{
        val listFragments= listOf(SNListFragment(0,"全部"),SNListFragment(1,"在库"),SNListFragment(2,"已出库"))
        return SNListPageAdapter(fragmentActivity.supportFragmentManager,listFragments)
    }
}