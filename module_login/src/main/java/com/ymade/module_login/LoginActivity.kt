package com.ymade.module_login

import android.text.TextUtils
import android.util.Log
import androidx.lifecycle.Observer
import com.alibaba.android.arouter.facade.annotation.Autowired
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.ymade.module_login.databinding.ActivityLoginBinding
import com.zcxie.zc.model_comm.base.BaseActivity
import com.zcxie.zc.model_comm.util.CommUtil


@Route(path = "/login/loginActivity")
class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }
    @Autowired(name = "dvIds")
    @JvmField
    var dvId: String? = null
    override fun processLogic() {
       mBinding?.let {
           Log.i(TAG, "processLogic: dvId $dvId")
           mBinding!!.passwordInputEt.setText(dvId)
           it.loginAck.setOnClickListener {
            var company= mBinding!!.phoneInputEt.text.toString()
               if (TextUtils.isEmpty(company)){
                   CommUtil.ToastU.showToast("请输入企业代码")
                   return@setOnClickListener
               }
               val devId: String = mBinding!!.passwordInputEt.text.toString()
               if (TextUtils.isEmpty(devId)||"-1"==dvId){
                   CommUtil.ToastU.showToast("设备编号获取失败")
                   return@setOnClickListener
               }
               showProgress("登录中..")
               mViewModel?.registerAndLogin(company,devId)?.observe(this, Observer {
                   hideProgress()
                   if (TextUtils.isEmpty(it)) {
                     ARouter.getInstance().build("/home/homeActivity1").navigation()
                       finish()
                   }else CommUtil.ToastU.showToast("登录失败")
               })
           }
       }
    }

    override fun findViewModelClass(): Class<LoginViewModel> {
      return LoginViewModel::class.java
    }
}