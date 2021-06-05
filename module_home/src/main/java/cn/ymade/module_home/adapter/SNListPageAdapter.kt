package cn.ymade.module_home.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentStatePagerAdapter
import cn.ymade.module_home.ui.fragment.SNListFragment
import cn.ymade.module_home.vm.VMListFragment

/**
 * @author zc.xie
 * @date 2021/6/5 0005.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
class SNListPageAdapter (val fm:FragmentManager,val fList:List<SNListFragment>):FragmentPagerAdapter(fm) {

    override fun getCount(): Int {
       return fList.size
    }

    override fun getItem(position: Int): Fragment {
        return fList[position]
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return fList[position].title
    }
}