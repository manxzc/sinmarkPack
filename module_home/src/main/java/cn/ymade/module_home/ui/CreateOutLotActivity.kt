package cn.ymade.module_home.ui

import android.content.Intent
import android.text.TextUtils
import cn.ymade.module_home.R
import cn.ymade.module_home.common.Constant
import cn.ymade.module_home.databinding.ActivityCreateOutBinding
import cn.ymade.module_home.db.beans.LotDataBean
import cn.ymade.module_home.homebase.ScanBaseActivity
import cn.ymade.module_home.vm.VMCraeteOutLot
import com.zcxie.zc.model_comm.callbacks.CallBack
import com.zcxie.zc.model_comm.util.CommUtil
import com.zcxie.zc.model_comm.util.LiveDataBus

/**
 * @author zc.xie
 * @date 2021/6/5 0005.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
class CreateOutLotActivity : ScanBaseActivity<VMCraeteOutLot,ActivityCreateOutBinding>() {

    override fun loadCoded(scanCode: String) {
//        mViewModel?.addScan(scanCode)
        mBinding!!.noTv.setText(scanCode)
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_create_out
    }

    override fun processLogic() {
        setTopTitle("新建出库")
        initBtmOnlyMind("确定")
        mBinding!!.timeTv.setText(CommUtil.getCurrentTimeHM())
        mViewModel!!.init(mBinding!!.rv,this)
    }

    override fun findViewModelClass(): Class<VMCraeteOutLot> {
        return VMCraeteOutLot::class.java
    }

    override fun clickOnlyMind() {
        super.clickOnlyMind()

        mBinding?.let {
            val date=it.timeTv.text.toString()
            val name=it.nameTv.text.toString()
            val No=it.noTv.text.toString()
            if (TextUtils.isEmpty(No)){
                CommUtil.ToastU.showToast("请输入批号~")
                return
            }

            showProgress("保存中")
            mViewModel!!.commit(No,name,object : CallBack<LotDataBean> {
                override fun callBack(data: LotDataBean) {
                    LiveDataBus.get().with(Constant.LD_UP_HOME_TITLE).postValue(1)
                    hideProgress()
                    startActivity(Intent( this@CreateOutLotActivity, LotInfoActivity::class.java).putExtra("selectLot",data).putExtra("showChange",1))
                    finish()
                }
            })
        }

    }

    override fun enableFastSuccess(): Boolean {

        return false
    }
}