package cn.ymade.module_home.adapter

import cn.ymade.module_home.R
import cn.ymade.module_home.databinding.ItemLotListBinding
import cn.ymade.module_home.db.beans.LotDataBean
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
class LotAdapter (val list: List<LotDataBean>,private val itemCallBack: CallBack<LotDataBean>) :
    BindBaseAdapter<LotDataBean>(list) {
    override fun getLayoutId(): Int {
        return R.layout.item_lot_list
    }

    override fun onBindViewHolder(holder: BindBaseViewHolder, position: Int) {
        var data=list[position]
        ( holder.binding as ItemLotListBinding).bean=data
        ( holder.binding as ItemLotListBinding).executePendingBindings()
        ( holder.binding as ItemLotListBinding).imgUp.setBackgroundResource(if (data.upload==1)  R.drawable.shape_up_bg else R.drawable.shape_noup_bg)
        ( holder.binding as ItemLotListBinding).tvUp.text=if (data.upload==1)  "已上传" else "未上传"
        holder.itemView.setOnClickListener {
            itemCallBack?.let {
                it.callBack(data)
            }
        }
    }
}