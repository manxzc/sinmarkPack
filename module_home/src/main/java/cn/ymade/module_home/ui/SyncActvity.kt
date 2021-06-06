package cn.ymade.module_home.ui

import android.view.View
import cn.ymade.module_home.R
import cn.ymade.module_home.databinding.ActivitySyncBinding
import cn.ymade.module_home.vm.VMSync
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.NiceDialog
import com.shehuan.nicedialog.ViewConvertListener
import com.shehuan.nicedialog.ViewHolder
import com.zcxie.zc.model_comm.base.BaseActivity

/**
 * @author zc.xie
 * @date 2021/6/4 0004.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
class SyncActvity :BaseActivity<VMSync,ActivitySyncBinding>() {
    override fun getLayoutId(): Int {
        return R.layout.activity_sync
    }

    override fun processLogic() {
        mViewModel!!.initAct(this)
        setTopTitle("同步")
    }

    fun startDown(v:View){
        createSyncDialog(1)
    }

    fun startUpLoad(v:View){
        createSyncDialog(2)
    }

    override fun findViewModelClass(): Class<VMSync> {
        return VMSync::class.java
    }
    private var niceDialog: NiceDialog? = null
    fun createSyncDialog(type: Int) {
        niceDialog = NiceDialog.init().setLayoutId(R.layout.dialog_text_chose_promapt)
        niceDialog?.setConvertListener(object : ViewConvertListener() {
            override fun convertView(holder: ViewHolder, dialog: BaseNiceDialog) {
                var title = ""
                var subTitle = ""
                if (type == 1) {
                    title = "下载"
                    subTitle = "下载货品数据 ~！"
                } else {
                    title = "上传"
                    subTitle = "上传货品数据~！"
                }
                holder.setText(R.id.dialog_tittle_tv, title)
                holder.setText(R.id.dialog_content_tv, subTitle)
                holder.getView<View>(R.id.dialog_promapt_cancle).setOnClickListener {
                    niceDialog?.dismiss()
                }
                holder.getView<View>(R.id.dialog_promapt_ack).setOnClickListener {
                    if (type==1)mViewModel?.download(this@SyncActvity)else mViewModel?.upload()
                    niceDialog?.dismiss()
                    showProgress("请稍后~")
                }
            }
        })?.setMargin(60)
            ?.show(supportFragmentManager)
    }
}