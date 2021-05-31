package com.zcxie.zc.model_comm.base

import android.util.Log
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

open abstract class BaseRecyclerViewAdapterKT<T>(val mDatas: List<T>) :RecyclerView.Adapter<BaseVH>(){
    private val TAG = javaClass.simpleName

    abstract fun convert(
        holder: BaseVH,
        data: T,
        position: Int
    )
    abstract fun getLayoutId(viewType: Int): Int

    abstract fun getItemViewTypes(poition: Int, data: List<T>?): Int

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseVH {
        Log.i(TAG, "onCreateViewHolder: ")
        return BaseVH.getVH(parent, getLayoutId(viewType))
    }

    override fun onBindViewHolder(holder: BaseVH, position: Int) {
        convert(holder, mDatas[position], position)
    }

    override fun getItemCount(): Int {
        return mDatas?.size ?: 0
    }

    override fun getItemViewType(position: Int): Int {
        return getItemViewTypes(position,mDatas)
    }
}