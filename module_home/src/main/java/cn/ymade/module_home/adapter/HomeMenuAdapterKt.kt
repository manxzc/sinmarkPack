package cn.ymade.module_home.adapter

import android.util.Log
import cn.ymade.module_home.R
import cn.ymade.module_home.databinding.ItemHomeMenuLayoutBinding
import cn.ymade.module_home.model.HomeMenuBean
import com.zcxie.zc.model_comm.base.BindBaseAdapter
import com.zcxie.zc.model_comm.base.BindBaseViewHolder
import com.zcxie.zc.model_comm.callbacks.CallBack

class HomeMenuAdapterKt(private val list: List<HomeMenuBean>, val itemCallBack: CallBack<HomeMenuBean>) :BindBaseAdapter<HomeMenuBean>(
    list
) {
    val TAG="HomeMenuAdapterKt"
    override fun getLayoutId(): Int {
        return R.layout.item_home_menu_layout
    }

    override fun onBindViewHolder(holder: BindBaseViewHolder, position: Int) {
        ( holder.binding as ItemHomeMenuLayoutBinding).bean=list[position]
        holder.binding.executePendingBindings()
        holder.itemView.setOnClickListener {
            Log.i(TAG, "onBindViewHolder: position $position")
            if (null != itemCallBack) itemCallBack.callBack(datas[position])
        }
    }
}