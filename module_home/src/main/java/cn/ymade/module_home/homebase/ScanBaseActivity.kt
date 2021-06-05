package cn.ymade.module_home.homebase

import android.text.TextUtils
import androidx.databinding.ViewDataBinding
import com.zcxie.zc.model_comm.base.BaseActivity
import com.zcxie.zc.model_comm.base.BaseViewModel

/**
 * @author zc.xie
 * @date 2021/6/5 0005.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
open abstract class ScanBaseActivity<VM : BaseViewModel?, VDB : ViewDataBinding?>  :BaseActivity<VM,VDB>() {


    abstract fun loadCoded(scanCode:String)
    override fun loadCode(value: String) {
        super.loadCode(value)
        if (!TextUtils.isEmpty(value))
        loadCoded(value)
    }
}