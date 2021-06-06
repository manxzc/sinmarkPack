package cn.ymade.module_home.ui

import android.content.Intent
import android.view.View
import cn.ymade.module_home.R
import cn.ymade.module_home.databinding.ActivityLotInfoBinding
import cn.ymade.module_home.db.beans.LotDataBean
import cn.ymade.module_home.homebase.ScanBaseActivity
import cn.ymade.module_home.vm.VMLotInfo
import com.zcxie.zc.model_comm.base.BaseActivity
import com.zcxie.zc.model_comm.util.LiveDataBus

/**
 * @author zc.xie
 * @date 2021/6/6 0006.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
class LotInfoActivity : ScanBaseActivity<VMLotInfo, ActivityLotInfoBinding>() {


    var isChange=false
    override fun getLayoutId(): Int {
        return R.layout.activity_lot_info
    }

    override fun processLogic() {
        setTopTitle("单据信息")
        val Lot=intent.getSerializableExtra("selectLot") as LotDataBean
        if (Lot==null){
            finish()
            return
        }
        showTopEdit(true)
        initBtmOnlyMind("删除")
        ll_only_parent?.visibility= View.GONE
        mBinding?.lotBean=Lot
        mViewModel!!.initLotSn(Lot,mBinding!!.rv,this,)
    }

    override fun onclickTopEdit() {
        super.onclickTopEdit()
        isChange=!isChange
        if(isChange){
            ll_only_parent?.visibility= View.VISIBLE
            setTopEdit(R.drawable.ic_save)
        }else{
            setTopEdit(R.drawable.edit_icon)
            ll_only_parent?.visibility= View.GONE
        }
        mViewModel!!.showDelete(isChange)

    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        setIntent(intent)
    }
    override fun findViewModelClass(): Class<VMLotInfo> {
        return VMLotInfo::class.java
    }

    override fun clickOnlyMind() {
        super.clickOnlyMind()
        mViewModel!!.deletLot()
    }

    override fun loadCoded(scanCode: String) {
        if (!isChange)
            return
        mViewModel?.addScan(scanCode)

    }
}