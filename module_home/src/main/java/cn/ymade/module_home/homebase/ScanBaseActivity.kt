package cn.ymade.module_home.homebase

import android.media.MediaPlayer
import android.text.TextUtils
import androidx.databinding.ViewDataBinding
import cn.ymade.module_home.R
import com.zcxie.zc.model_comm.base.BaseActivity
import com.zcxie.zc.model_comm.base.BaseViewModel

/**
 * @author zc.xie
 * @date 2021/6/5 0005.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
open abstract class ScanBaseActivity<VM : BaseViewModel?, VDB : ViewDataBinding?>  :BaseActivity<VM, VDB>() {

    var success = MediaPlayer.create(applicationContext, R.raw.success)
    var fail = MediaPlayer.create(applicationContext, R.raw.fail)
    abstract fun loadCoded(scanCode: String)
    abstract fun enableFastSuccess():Boolean
    override fun loadCode(value: String) {
        super.loadCode(value)
        if (!TextUtils.isEmpty(value)) {
            if (enableFastSuccess()){
                success.start()
            }
            loadCoded(value)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        success.release()
        fail.release()
    }
}