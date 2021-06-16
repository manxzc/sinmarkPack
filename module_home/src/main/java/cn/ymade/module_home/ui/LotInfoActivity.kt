package cn.ymade.module_home.ui

import android.content.Intent
import android.util.Log
import android.view.View
import cn.ymade.module_home.R
import cn.ymade.module_home.common.Constant
import cn.ymade.module_home.databinding.ActivityLotInfoBinding
import cn.ymade.module_home.db.beans.LotDataBean
import cn.ymade.module_home.db.database.DataBaseManager
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
    var sendChange=false
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
        mViewModel!!.initLotSn(Lot,mBinding!!.rv,mBinding!!.rvTitle,this)

//        Thread{
//            Log.i(TAG, "processLogic: "+DataBaseManager.db.snDao().testSubQuery(Lot.LotSN).size)
////            var cursor=DataBaseManager.db.snDao().testSubQueryCursor(Lot.LotSN)
////            Log.i(TAG, "processLogic: cursor count "+cursor.count)
////            for ( i in cursor.count-1 downTo 0){
////                cursor.move(i)
////                Log.i(TAG, "processLogic:getColumnName "+cursor.getColumnName(i))
////                Log.i(TAG, "processLogic:getColumnName 0 "+cursor.getString(0))
////                Log.i(TAG, "processLogic:getColumnName 2 "+cursor.getString(2))
////                Log.i(TAG, "processLogic:getColumnName 3 "+cursor.getString(3))
////                Log.i(TAG, "processLogic:getColumnName 4 "+cursor.getString(5))
////            }
//        }.start()

        mBinding!!.tabSnList.setOnClickListener {
            mBinding!!.rv.visibility=View.VISIBLE
            mBinding!!.rvTitle.visibility=View.GONE
            mBinding!!.tabSnList.setTextColor(resources.getColor(R.color.main_blue))
            mBinding!!.tabTitleList.setTextColor(resources.getColor(R.color.color_666666))
        }

        mBinding!!.tabTitleList.setOnClickListener {
            mBinding!!.rvTitle.visibility=View.VISIBLE
            mBinding!!.rv.visibility=View.GONE
            mBinding!!.tabTitleList.setTextColor(resources.getColor(R.color.main_blue))
            mBinding!!.tabSnList.setTextColor(resources.getColor(R.color.color_666666))
        }

    }

    override fun onclickTopEdit() {
        super.onclickTopEdit()
//        mViewModel?.addScan("1000008")
//        mViewModel?.addScan("1000009")
        isChange=!isChange
        if(isChange){
            setTopEdit(R.drawable.ic_save)
            initBtmOnlyMind("删除")
//            mViewModel?.addScan("1000007")
        }else{
            setTopEdit(R.drawable.edit_icon)
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
        sendChange=true
        if (getBtmOnlyMindText()=="上传"){
            mViewModel?.upLoadLotInfo()
        }else if (getBtmOnlyMindText()=="删除"){
            mViewModel?.deletLot()
        }
    }

    override fun loadCoded(scanCode: String) {
        sendChange=true
        mViewModel?.addScan(scanCode)
    }
    
    override fun onStop() {
        super.onStop()
        if (sendChange){
            sendChange=false
            LiveDataBus.get().with(Constant.LD_UP_HOME_TITLE).postValue(1)
            LiveDataBus.get().with("notyChange").postValue("1")
        }
    }

    override fun enableFastSuccess(): Boolean {
        return false
    }
}