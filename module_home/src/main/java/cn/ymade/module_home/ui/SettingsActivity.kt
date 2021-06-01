package cn.ymade.module_home.ui

import cn.ymade.module_home.R
import cn.ymade.module_home.databinding.ActivitySettingBinding
import cn.ymade.module_home.vm.VMSetting
import com.zcxie.zc.model_comm.base.BaseActivity

/**
 * @author zc.xie
 * @date 2021/6/1 0001.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
class SettingsActivity :BaseActivity<VMSetting,ActivitySettingBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_setting
    }

    override fun processLogic() {
        mBinding?.vm=mViewModel
    }

    override fun findViewModelClass(): Class<VMSetting> {
        return VMSetting::class.java
    }
}