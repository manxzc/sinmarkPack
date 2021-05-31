package com.zcxie.zc.model_comm.base

abstract class SmartAdapter<T>(data: List<T>):BaseRecyclerViewAdapterKT<T>(data) {
    private var selectIndex=0;
     override fun convert(holder: BaseVH, data: T, position: Int) {
         dealView(holder, data, position)
     }

     override fun getLayoutId(viewType: Int): Int {
         return getLayout(viewType)
     }

     override fun getItemViewTypes(poition: Int, data: List<T>?): Int {
         return poition
     }

    abstract fun getLayout(viewType: Int): Int

    abstract fun dealView(holder: BaseVH?, data: T, position: Int)

    open fun initSelectIndex(selectIndex: Int) {
        this.selectIndex = selectIndex
    }

    open fun selectIndex(selectIndex: Int) {
        this.selectIndex = selectIndex
        notifyDataSetChanged()
    }

    open fun getSelectIndex(): Int {
        return selectIndex
    }
 }