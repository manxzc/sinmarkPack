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
 * GitHub：xzc
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
        initBtmOnlyMind("上传")
        mBinding?.lotBean=Lot
        mViewModel!!.initLotSn(Lot,mBinding!!.rv,this,)

    }

    override fun onclickTopEdit() {
        super.onclickTopEdit()
//        mViewModel?.addScan("1000008")
//        mViewModel?.addScan("1000009")
        isChange=!isChange
        if(isChange){
            ll_only_parent?.visibility= View.VISIBLE
            setTopEdit(R.drawable.ic_save)
            initBtmOnlyMind("删除")
//            mViewModel?.addScan("1000007")
        }else{
            setTopEdit(R.drawable.edit_icon)
            ll_only_parent?.visibility= View.GONE
            initBtmOnlyMind("上传")
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
        if (getBtmOnlyMindText()=="上传"){
            mViewModel?.upLoadLotInfo()
        }else if (getBtmOnlyMindText()=="删除"){
            mViewModel?.upLoadLotInfo()
        }
    }

    override fun loadCoded(scanCode: String) {
        mViewModel?.addScan(scanCode)
    }
    fun refresh(difTitleCount:String){
        mBinding!!.titleDifCount.text= "型号种类   $difTitleCount"
    }
}