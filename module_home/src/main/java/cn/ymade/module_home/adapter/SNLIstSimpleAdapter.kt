package cn.ymade.module_home.adapter

import cn.ymade.module_home.R
import cn.ymade.module_home.databinding.ItemListSimpleBinding
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
class SNLIstSimpleAdapter (val list: List<SNBean>,private val itemCallBack: CallBack<SNBean>) :
    BindBaseAdapter<SNBean>(list) {
    override fun getLayoutId(): Int {
        return R.layout.item_list_simple
    }

    override fun onBindViewHolder(holder: BindBaseViewHolder, position: Int) {
        var data=list[position]
        ( holder.binding as ItemListSimpleBinding).bean=data
        ( holder.binding as ItemListSimpleBinding).executePendingBindings()
        holder.itemView.setOnClickListener {
            itemCallBack?.let {
                it.callBack(data)
            }
        }
    }
}