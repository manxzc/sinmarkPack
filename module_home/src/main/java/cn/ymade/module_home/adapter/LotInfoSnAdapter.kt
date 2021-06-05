package cn.ymade.module_home.adapter

import cn.ymade.module_home.R
import cn.ymade.module_home.databinding.ItemOutlotInfoSnBinding
import cn.ymade.module_home.databinding.ItemSimpleBinding
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
class LotInfoSnAdapter (val list: List<SNBean>, val callBack: CallBack<SNBean>) :
    BindBaseAdapter<SNBean>(list) {
    override fun getLayoutId(): Int {
        return R.layout.item_outlot_info_sn
    }

    override fun onBindViewHolder(holder: BindBaseViewHolder, position: Int) {
        var data=list[position]
        ( holder.binding as ItemOutlotInfoSnBinding).bean=data
        ( holder.binding as ItemOutlotInfoSnBinding).executePendingBindings()
        ( holder.binding as ItemOutlotInfoSnBinding).assetSelectDelectIv.setOnClickListener {
            callBack?.let {
                it.callBack(data)
            }
        }
    }
}