package com.zcxie.zc.model_comm.base

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView

abstract class BindBaseAdapter<T>(val datas: List<T>) :RecyclerView.Adapter<BindBaseViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BindBaseViewHolder {

       return BindBaseViewHolder(
           DataBindingUtil.inflate(
               LayoutInflater.from(parent.context),
               getLayoutId(),
               parent,
               false
           )
       )
    }

    abstract fun getLayoutId(): Int

    override fun getItemCount(): Int {
        return datas.size
    }
}