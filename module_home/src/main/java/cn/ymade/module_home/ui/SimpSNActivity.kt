package cn.ymade.module_home.ui

import android.view.View
import cn.ymade.module_home.R
import cn.ymade.module_home.databinding.ActivitySimplesnBinding
import cn.ymade.module_home.vm.VMSimpleSN
import com.zcxie.zc.model_comm.base.BaseActivity
import com.zcxie.zc.model_comm.util.LiveDataBus

/**
 * @author zc.xie
 * @date 2021/6/5 0005.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
class SimpSNActivity :BaseActivity<VMSimpleSN,ActivitySimplesnBinding>(){
    override fun getLayoutId(): Int {
        return R.layout.activity_simplesn
    }

    override fun processLogic() {
        initTopSearchBar()
        mViewModel!!.initData(mBinding!!.rvSimple,this)

    }

    override fun findViewModelClass(): Class<VMSimpleSN> {
       return VMSimpleSN::class.java
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