package cn.ymade.module_home.ui

import android.content.Intent
import android.view.View
import cn.ymade.module_home.R
import cn.ymade.module_home.databinding.ActivitySettingBinding
import cn.ymade.module_home.vm.VMSetting
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.NiceDialog
import com.shehuan.nicedialog.ViewConvertListener
import com.shehuan.nicedialog.ViewHolder
import com.zcxie.zc.model_comm.base.BaseActivity
import com.zcxie.zc.model_comm.base.BaseApplication
import com.zcxie.zc.model_comm.util.CommUtil

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
        setTopTitle("设备信息")
        mBinding?.let {
            it.versiontip.visibility=if (CommUtil.getPackageName()==mViewModel?.getUserInfo()?.value?.Version) View.VISIBLE else View.GONE

            it.btnLogout.setOnClickListener {
                createLoginOutDialog()
            }
        }

    }

    override fun findViewModelClass(): Class<VMSetting> {
        return VMSetting::class.java
    }
    private var niceDialog: NiceDialog? = null
    fun createLoginOutDialog() {
        niceDialog = NiceDialog.init().setLayoutId(R.layout.dialog_text_chose_promapt)
        niceDialog?.setConvertListener(object : ViewConvertListener() {
            override fun convertView(holder: ViewHolder, dialog: BaseNiceDialog) {
                holder.setText(R.id.dialog_tittle_tv, "设备注销")
                holder.setText(R.id.dialog_content_tv, "确定注销当前设备？ ")
                holder.getView<View>(R.id.dialog_promapt_cancle)
                    .setOnClickListener { niceDialog?.dismiss() }
                holder.getView<View>(R.id.dialog_promapt_ack).setOnClickListener {
                    niceDialog?.dismiss()
                    mViewModel?.loginOut()
                }
            }
        })?.setMargin(60)
            ?.show(supportFragmentManager)
    }
}