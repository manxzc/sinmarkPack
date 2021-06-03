package cn.ymade.module_home.ui

import cn.ymade.module_home.R
import cn.ymade.module_home.databinding.ActivityStaffBinding
import cn.ymade.module_home.db.beans.StaffBean
import cn.ymade.module_home.vm.VMStaff
import com.zcxie.zc.model_comm.base.BaseActivity
import com.zcxie.zc.model_comm.callbacks.CallBack
import com.zcxie.zc.model_comm.util.LiveDataBus

/**
 * @author zc.xie
 * @date 2021/6/3 0003.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
class StaffActivity:BaseActivity<VMStaff,ActivityStaffBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_staff
    }

    override fun processLogic() {
        setTopTitle("部门人员")
        initBtmOnlyMind("确定")
        mViewModel?.let {
            mBinding?.groupRv?.let { rv -> it.initDepart(rv) }
            mBinding?.staffRv?.let { staffRv -> it.initStaff(staffRv) }
        }
    }
    override fun clickOnlyMind(){
        mViewModel!!.commit(object :CallBack<StaffBean>{
            override fun callBack(data: StaffBean) {
                LiveDataBus.get().with("changeStaff").postValue(data)
                finish()
            }
        })
    }


    override fun findViewModelClass(): Class<VMStaff> {
        return VMStaff::class.java
    }
}