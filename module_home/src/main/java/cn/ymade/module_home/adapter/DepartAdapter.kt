package cn.ymade.module_home.adapter

import android.graphics.Color
import android.util.Log
import cn.ymade.module_home.R
import cn.ymade.module_home.databinding.ItemGroupSelectLayoutBinding
import cn.ymade.module_home.db.beans.DepartBean
import com.zcxie.zc.model_comm.base.BindBaseAdapter
import com.zcxie.zc.model_comm.base.BindBaseViewHolder
import com.zcxie.zc.model_comm.callbacks.CallBack

/**
 * @author zc.xie
 * @date 2021/6/3 0003.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
class DepartAdapter(val list: List<DepartBean>, val callBack: CallBack<DepartBean>) :BindBaseAdapter<DepartBean>(list) {

    override fun getLayoutId(): Int {

        return R.layout.item_group_select_layout
    }

    var lastSelectIndex=-1;
    override fun onBindViewHolder(holder: BindBaseViewHolder, position: Int) {

        var data=list[position]
        ( holder.binding as ItemGroupSelectLayoutBinding ).bean=data
        holder.binding.executePendingBindings()
        ( holder.binding as ItemGroupSelectLayoutBinding ).llGroupSelect.isSelected= data.current==1

        if (data.current==1){
            lastSelectIndex=position
        }
        holder.itemView.setOnClickListener {
            Log.i("TAG", "onBindViewHolder:  Depart click item "+position)
            callBack?.let {
                it.callBack(list[position])
                if (lastSelectIndex!=-1)
                list[lastSelectIndex].current=0
                data.current=1
                lastSelectIndex=position

                notifyDataSetChanged()
            }
        }

    }
}