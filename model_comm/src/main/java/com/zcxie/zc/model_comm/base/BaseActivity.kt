package com.zcxie.zc.model_comm.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.barlibrary.ImmersionBar

//ViewDataBinding 是所有DataBinding的父类

 abstract class BaseActivity<VM : BaseViewModel?, VDB : ViewDataBinding?> :
    AppCompatActivity() {
    val TAG=javaClass.simpleName

    //获取contentView  初始化binding
    abstract fun getLayoutId(): Int
    //处理逻辑业务
    abstract fun processLogic()
    var mViewModel: VM? = null
    var mBinding: VDB? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ImmersionBar.with(this).fullScreen(true).statusBarDarkFont(true).navigationBarEnable(false)
            .init()
        //初始化binding
        mBinding = DataBindingUtil.setContentView<VDB>(this, getLayoutId())
        //给binding加上感知生命周期，AppCompatActivity就是lifeOwner，
        mBinding!!.lifecycleOwner = this
        //创建我们的ViewModel。
        mViewModel= ViewModelProvider(this).get(findViewModelClass())
        processLogic()
    }
    abstract fun findViewModelClass():Class<VM>
//    fun createViewModel() {
//        if (mViewModel == null) {
//            val modelClass: Class<*>
//            //ParameterizedType获取java泛型参数类型
////            getClass().getGenericSuperclass()
////            返回表示此 Class 所表示的实体（类、接口、基本类型或 void）的直接超类的 Type，然后将其转换ParameterizedType。
////            getActualTypeArguments()
////            返回表示此类型实际类型参数的 Type 对象的数组。[0]就是这个数组中第一个了。简而言之就是获得超类的泛型参数的实际类型。
////
//            val type = javaClass.genericSuperclass
//            modelClass = if (type is ParameterizedType) {
//                type.actualTypeArguments[0] as Class<*>
//            } else {
//                //如果没有指定泛型参数，则默认使用BaseViewModel
//                BaseViewModel::class.java
//            }
//            //kotlin中泛型 需要用as转换成指定类型
//            mViewModel = ViewModelProviders.of(this).get<BaseViewModel>(modelClass as Class<BaseViewModel>) as VM
//        }
//    }
}