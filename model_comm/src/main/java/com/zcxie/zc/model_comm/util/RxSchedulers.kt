package com.zcxie.zc.model_comm.util

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.ObservableTransformer
import io.reactivex.rxjava3.schedulers.Schedulers

/**
 * @author zc.xie
 * @date 2021/6/2 0002.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
class RxSchedulers {

    companion object {

        fun <T> io2main(): ObservableTransformer<T, T> {

            return ObservableTransformer { upstream ->
                upstream.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())

            }

        }

    }

}