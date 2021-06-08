package cn.ymade.module_home.adapter

import android.view.View
import cn.ymade.module_home.R
import cn.ymade.module_home.databinding.*
import cn.ymade.module_home.db.beans.SNBean
import cn.ymade.module_home.db.beans.StaffBean
import cn.ymade.module_home.model.SNTitleBean
import cn.ymade.module_home.utils.ImageUtil
import com.zcxie.zc.model_comm.base.BindBaseAdapter
import com.zcxie.zc.model_comm.base.BindBaseViewHolder
import com.zcxie.zc.model_comm.callbacks.CallBack

/**
 * @author zc.xie
 * @date 2021/6/5 0005.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
class SnTitleAdapter (val list: List<SNTitleBean>, val callBack: CallBack<SNTitleBean>) :
    BindBaseAdapter<SNTitleBean>(list) {
    var showDelete=true
    override fun getLayoutId(): Int {
        return R.layout.item_lot_info_sntitle
    }
    fun showDelete(show:Boolean){
        showDelete=show
    }

    override fun onBindViewHolder(holder: BindBaseViewHolder, position: Int) {
        var data=list[position]
        ( holder.binding as ItemLotInfoSntitleBinding).bean=data
        ( holder.binding as ItemLotInfoSntitleBinding).executePendingBindings()
        if (showDelete) {
            (holder.binding as ItemLotInfoSntitleBinding).assetSelectDelectIv.visibility= View.VISIBLE
            (holder.binding as ItemLotInfoSntitleBinding).assetSelectDelectIv.setOnClickListener {
                callBack?.let {
                    it.callBack(data)
                }
            }
        }else{
            (holder.binding as ItemLotInfoSntitleBinding).assetSelectDelectIv.visibility= View.GONE
        }
    }
}