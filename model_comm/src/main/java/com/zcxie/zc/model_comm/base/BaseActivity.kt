package com.zcxie.zc.model_comm.base

import android.app.ProgressDialog
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.barlibrary.ImmersionBar
import com.zcxie.zc.model_comm.R
import com.zcxie.zc.model_comm.callbacks.CallBack
import com.zcxie.zc.model_comm.util.CommUtil
import com.zcxie.zc.model_comm.util.EditViewUtil

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

        val intentFilter = IntentFilter()
        for (s in ACTION_SCANRESULTs) {
            intentFilter.addAction(s)
        }
        registerReceiver(broadcastReceiver, intentFilter)
    }
    abstract fun findViewModelClass():Class<VM>

     private var tv_TopTitle: TextView? = null
     private var backImg: ImageView? = null
     private var setting_icon :ImageView?=null
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
         setting_icon=findViewById(R.id.setting_icon)
         setting_icon?.setOnClickListener {
             onclickTopEdit()
         }
     }
     fun setTopEdit(resId:Int){
             setting_icon?.setImageResource(resId)
     }
     fun showTopEdit(show :Boolean){
         if (show)
             setting_icon?.visibility=View.VISIBLE
         else
             setting_icon?.visibility=View.GONE
     }
     open fun onclickTopEdit(){}


     private var mid_tv: TextView? = null
     var ll_only_parent:View?=null
     fun initBtmOnlyMind(text: String?){
         ll_only_parent=findViewById(R.id.ll_only_parent)
         mid_tv=findViewById(R.id.mid_tv)
         text?.let {
             mid_tv!!.text = text
         }
         mid_tv!!.setOnClickListener {
             clickOnlyMind()
         }
     }
     open fun clickOnlyMind(){}

     fun getBtmOnlyMindText():String{
         return mid_tv?.text.toString()
     }

     fun closeCurrentAct(){
         finish()
     }

     override fun onDestroy() {
         super.onDestroy()
         BaseApplication.getInstance()?.actList?.remove(this)
         ImmersionBar.with(this).destroy()
         unregisterReceiver(broadcastReceiver)
     }

     val ACTION_SCANRESULTs = arrayOf(
         "android.intent.action.SCANRESULT",
         "android.intent.ACTION_DECODE_DATA"
     ) //扫描广播

     val ACTION_SACNRESULT_VALUE = "value" //扫描值value

     val ACTION_SACNRESULT_BAR_CODE_STRING = "barcode_string" //扫描值value

     val ACTION_SACNRESULT_BARCODE_VALUE = "barcode_string" //扫描值value

     var broadcastReceiver: BroadcastReceiver = object : BroadcastReceiver() {
         override fun onReceive(context: Context, intent: Intent) {
             try {
                 Log.i("TAG", "onReceive:  ")
                 if (intent != null) {
                     if (intent.hasExtra(ACTION_SACNRESULT_VALUE)) {
                         var value = intent.getStringExtra(ACTION_SACNRESULT_VALUE)
                         if (TextUtils.isEmpty(value)) value =
                             intent.getStringExtra(ACTION_SACNRESULT_BAR_CODE_STRING)
                         value?.let { loadCode(it) }
                     } else if (intent.hasExtra(ACTION_SACNRESULT_BARCODE_VALUE)) {
                         var value = intent.getStringExtra(ACTION_SACNRESULT_VALUE)
                         if (TextUtils.isEmpty(value)) value =
                             intent.getStringExtra(ACTION_SACNRESULT_BAR_CODE_STRING)
                         value?.let { loadCode(it) }
                     }
                 }
             } catch (e: Exception) {
                 Toast.makeText(context, "出错了 $e", Toast.LENGTH_LONG).show()
             }
         }
     }

     open fun loadCode(value: String) {
         Log.i("TAG", "loadCode: $value")
     }

     private var progressDialog: ProgressDialog? = null
     open fun getProgressDialog(): ProgressDialog? {
         if (null == progressDialog) {
             progressDialog = ProgressDialog(this) //实例化progressDialog
//             progressDialog!!.setCancelable(false)
             progressDialog!!.setCanceledOnTouchOutside(false)
         }
         return progressDialog
     }
     open fun showProgress(msg: String?) {
         getProgressDialog()!!.setMessage(msg) //设置进度条加载内容
         if (!progressDialog!!.isShowing) //如果进度条没有显示
             progressDialog!!.show() //显示进度条
     }

     open fun hideProgress() {
         if (isShowProgress()) progressDialog!!.dismiss()
     }
     open fun isShowProgress(): Boolean {
         return progressDialog != null && progressDialog!!.isShowing
     }
     private var searchEt: EditText? = null

     open fun onSearchAction(s: String) {}
     open fun hideTitle() {
         findViewById<View>(R.id.parent_layout).setVisibility(View.GONE)
     }
     open fun initTopSearchBar() {
         hideTitle()
         searchEt = findViewById(R.id.search_et)
         EditViewUtil.EditActionListener(searchEt, object : CallBack<String> {
             override fun callBack(obj: String) {
                 onSearchAction(obj)
                 CommUtil.hideKeyboard(searchEt)
             }
         })
         EditViewUtil.EditDatachangeLister(searchEt, object : CallBack<String> {
             override fun callBack(obj: String?) {
                 if (TextUtils.isEmpty(obj)) {
                     onSearchAction("")
                     CommUtil.hideKeyboard(searchEt)
                 }
             }
         })
     }

     open fun setDoSeach( s: String){
         searchEt?.setText(s)
         onSearchAction(s)
     }
     open fun clickback(searchback: View?) {
         finish()
     }
}