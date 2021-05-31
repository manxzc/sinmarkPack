package com.ymade.module_login

import android.content.Intent
import android.text.TextUtils
import androidx.lifecycle.Observer
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

    override fun processLogic() {
       mBinding?.let {
           mBinding!!.passwordInputEt.setText("12312")
           it.loginAck.setOnClickListener {
            var company= mBinding!!.phoneInputEt.text.toString()
               if (TextUtils.isEmpty(company)){
                   CommUtil.ToastU.showToast("请输入企业代码")
                   return@setOnClickListener
               }
               val passStr: String = mBinding!!.passwordInputEt.text.toString()
               if (TextUtils.isEmpty(passStr)){
                   CommUtil.ToastU.showToast("设备编号获取失败")
                   return@setOnClickListener
               }
               mViewModel?.registerAndLogin(company,passStr)?.observe(this, Observer {
                   if (TextUtils.isEmpty(it)) {
                     ARouter.getInstance().build("/home/homeActivity").navigation()
                   }else CommUtil.ToastU.showToast("登录失败")
               })
           }
       }
    }

    override fun findViewModelClass(): Class<LoginViewModel> {
      return LoginViewModel::class.java
    }
}