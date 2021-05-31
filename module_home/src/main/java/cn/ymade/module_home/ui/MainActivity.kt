package cn.ymade.module_home.ui

import cn.ymade.module_home.R
import cn.ymade.module_home.databinding.ActivityMainBinding
import cn.ymade.module_home.vm.VMMain
import com.zcxie.zc.model_comm.base.BaseActivity

class MainActivity : BaseActivity<VMMain, ActivityMainBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_main
    }

    override fun processLogic() {
        startMainTo()
    }

    override fun findViewModelClass(): Class<VMMain> {
        return VMMain::class.java
    }
    private fun startMainTo() {
        mViewModel?.startMain(this)
    }
}