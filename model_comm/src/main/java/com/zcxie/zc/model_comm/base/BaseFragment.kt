package com.zcxie.zc.model_comm.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders

abstract class BaseFragment<VM : BaseViewModel?, VDB : ViewDataBinding?> :Fragment() {
    val TAG= javaClass.simpleName!!
    var mViewModel: VM?=null
    var  mBinding: VDB?=null

    abstract fun getLayoutId(): Int
    abstract fun findViewModelClass():Class<VM>
    abstract fun initView()
    abstract fun initData()
    abstract fun initEvent()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mBinding= DataBindingUtil.inflate<VDB>(inflater, getLayoutId(), container, false)
        mBinding!!.lifecycleOwner = this
        mViewModel= ViewModelProvider(this).get(findViewModelClass())
        initView()
        initData()
        initEvent()
        return mBinding!!.root
    }
}