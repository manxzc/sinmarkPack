package cn.ymade.module_home.ui

import cn.ymade.module_home.R
import cn.ymade.module_home.databinding.ActivityHomBinding
import com.alibaba.android.arouter.facade.annotation.Route
import cn.ymade.module_home.vm.VMHome
import com.zcxie.zc.model_comm.base.BaseActivity
@Route(path = "/home/homeActivity")
class HomeActivity :BaseActivity<VMHome, ActivityHomBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_hom
    }
    override fun processLogic() {
    }

    override fun findViewModelClass(): Class<VMHome> {
        return VMHome::class.java
    }

}