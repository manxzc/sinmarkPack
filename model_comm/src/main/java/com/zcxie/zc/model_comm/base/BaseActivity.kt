package com.zcxie.zc.model_comm.base

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.barlibrary.ImmersionBar
import com.zcxie.zc.model_comm.R

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
        // ARouter 注意一定要注入才能接收到参数
        ARouter.getInstance().inject(this)
        //初始化binding
        mBinding = DataBindingUtil.setContentView<VDB>(this, getLayoutId())
        //给binding加上感知生命周期，AppCompatActivity就是lifeOwner，
        mBinding!!.lifecycleOwner = this
        //创建我们的ViewModel。
        mViewModel= ViewModelProvider(this).get(findViewModelClass())
        initTopBar()

        processLogic()
        BaseApplication.getInstance()?.actList?.add(this)
    }
    abstract fun findViewModelClass():Class<VM>

     private var tv_TopTitle: TextView? = null
     private var backImg: ImageView? = null
     public fun setTopTitle(text: String?) {
         if (tv_TopTitle != null) {
             tv_TopTitle!!.text = text
         }
     }
     private  fun initTopBar() {
         tv_TopTitle = findViewById(R.id.root_title_tv)
         backImg = findViewById(R.id.root_back_btn)
         backImg?.setOnClickListener {
             closeCurrentAct()
         }
     }


     private var mid_tv: TextView? = null

     fun initBtmOnlyMind(text: String?){
         mid_tv=findViewById(R.id.mid_tv)
         text?.let {
             mid_tv!!.text = text
         }
         mid_tv!!.setOnClickListener {
             clickOnlyMind()
         }
     }
     open fun clickOnlyMind(){}

     fun closeCurrentAct(){
         finish()
     }

     override fun onDestroy() {
         super.onDestroy()
         BaseApplication.getInstance()?.actList?.remove(this)
     }

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