package cn.ymade.module_home.ui

import android.view.View
import cn.ymade.module_home.R
import cn.ymade.module_home.databinding.ActivitySimplesnBinding
import cn.ymade.module_home.db.beans.LotDataBean
import cn.ymade.module_home.model.SNTitleBean
import cn.ymade.module_home.vm.VMNewSimpleSN
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
class NewSimpSNActivity :BaseActivity<VMNewSimpleSN,ActivitySimplesnBinding>(){
    override fun getLayoutId(): Int {
        return R.layout.activity_simplesn
    }

    override fun processLogic() {
        initTopSearchBar()
        mViewModel!!.initData(mBinding!!.rvSimple,this)
        val Lot=intent.getSerializableExtra("selectLot") as SNTitleBean
        if (Lot!=null){
            mViewModel!!.updateUI(this,Lot.snBeans)
            return
        }

    }

    override fun findViewModelClass(): Class<VMNewSimpleSN> {
       return VMNewSimpleSN::class.java
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