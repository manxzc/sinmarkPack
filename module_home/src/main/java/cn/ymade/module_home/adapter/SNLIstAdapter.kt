package cn.ymade.module_home.adapter

import cn.ymade.module_home.R
import cn.ymade.module_home.databinding.ItemSnlistBinding
import cn.ymade.module_home.databinding.ItemStaffSelectLayoutBinding
import cn.ymade.module_home.db.beans.SNBean
import cn.ymade.module_home.db.beans.StaffBean
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
class SNLIstAdapter (val list: List<SNBean>, val callBack: CallBack<SNBean>) :
    BindBaseAdapter<SNBean>(list) {
    override fun getLayoutId(): Int {
        return R.layout.item_snlist
    }

    override fun onBindViewHolder(holder: BindBaseViewHolder, position: Int) {
        var data=list[position]
        ( holder.binding as ItemSnlistBinding).bean=data
        ( holder.binding as ItemSnlistBinding).executePendingBindings()
        ( holder.binding as ItemSnlistBinding).imgUp.setBackgroundResource(if (data.out==1)  R.drawable.shape_up_bg else R.drawable.shape_noup_bg)
        ( holder.binding as ItemSnlistBinding).tvUp.text=if (data.out==1)  "出库" else "在库"
        holder.itemView.setOnClickListener {
            callBack?.let {
                it.callBack(data)
            }
        }
    }
}