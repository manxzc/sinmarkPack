package cn.ymade.module_home.adapter

import cn.ymade.module_home.R
import cn.ymade.module_home.databinding.ItemGroupSelectLayoutBinding
import cn.ymade.module_home.databinding.ItemStaffSelectLayoutBinding
import cn.ymade.module_home.db.beans.DepartBean
import cn.ymade.module_home.db.beans.StaffBean
import cn.ymade.module_home.model.HomeMenuBean
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
class StaffAdapter(val list: List<StaffBean>, val callBack: CallBack<StaffBean>) :BindBaseAdapter<StaffBean>(list) {
    override fun getLayoutId(): Int {

        return R.layout.item_staff_select_layout
    }
    var lastSelectIndex=-1;
    fun clearSelect(){
        lastSelectIndex=-1
    }

    override fun onBindViewHolder(holder: BindBaseViewHolder, position: Int) {

        val data=list[position]
        ( holder.binding as ItemStaffSelectLayoutBinding ).bean=data
        holder.binding.executePendingBindings()
        ( holder.binding as ItemStaffSelectLayoutBinding ).llStaffSelect.isSelected= data.current==1
        if (data.current==1)
            lastSelectIndex=position

        holder.itemView.setOnClickListener {
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