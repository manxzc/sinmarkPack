package cn.ymade.module_home.ui

import android.text.TextUtils
import android.view.View
import cn.ymade.module_home.R
import cn.ymade.module_home.databinding.ActivitySearchsnBinding
import cn.ymade.module_home.databinding.FragmentSnlistBinding
import cn.ymade.module_home.vm.VMListFragment
import cn.ymade.module_home.vm.VMSNSearch
import com.zcxie.zc.model_comm.base.BaseActivity

/**
 * @author zc.xie
 * @date 2021/6/5 0005.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
class SNSearchActivity :BaseActivity<VMSNSearch,ActivitySearchsnBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_searchsn
    }

    override fun processLogic() {
        initTopSearchBar()
        mViewModel!!.initData(mBinding!!.rv,this)
    }

    override fun findViewModelClass(): Class<VMSNSearch> {
        return VMSNSearch::class.java
    }

    override fun onSearchAction(s: String) {
        super.onSearchAction(s)
        mViewModel!!.searchData(s)
    }

    override fun loadCode(value: String) {
        super.loadCode(value)
        setDoSeach(value)
    }
    fun reloadTitle(text:String){
        mBinding!!.count.text=text
    }
    fun showNotData(show:Boolean){
        if (show){
            mBinding!!.assetListEmpty.visibility= View.VISIBLE
        }else{
            mBinding!!.assetListEmpty.visibility= View.GONE
        }
    }
}