package cn.ymade.module_home.adapter

import android.view.View
import cn.ymade.module_home.R
import cn.ymade.module_home.databinding.ItemOutlotInfoSnBinding
import cn.ymade.module_home.db.beans.SNBean
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
class LotInfoSnAdapter (val list: List<SNBean>, private val deleteCallBack: CallBack<SNBean>) :
    BindBaseAdapter<SNBean>(list) {
    var showDelete=true
    override fun getLayoutId(): Int {
        return R.layout.item_outlot_info_sn
    }
    fun showDelete(show:Boolean){
        showDelete=show
    }

    override fun onBindViewHolder(holder: BindBaseViewHolder, position: Int) {
        var data=list[position]
        ( holder.binding as ItemOutlotInfoSnBinding).bean=data
        ( holder.binding as ItemOutlotInfoSnBinding).executePendingBindings()
        if (showDelete) {
            (holder.binding as ItemOutlotInfoSnBinding).assetSelectDelectIv.visibility= View.VISIBLE
            (holder.binding as ItemOutlotInfoSnBinding).assetSelectDelectIv.setOnClickListener {
                deleteCallBack?.let {
                    it.callBack(data)
                }
            }
        }else{
            (holder.binding as ItemOutlotInfoSnBinding).assetSelectDelectIv.visibility= View.GONE
        }
    }
}