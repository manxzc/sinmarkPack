package com.zcxie.zc.model_comm.net

import com.zcxie.zc.model_comm.util.AppConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import tech.nicesky.retrofit2_adapter_rxjava3.RxJava3CallAdapterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit

 class RetrofitManager<T> {
    companion object {
        //项目中设置头信息
        val headerInterceptor: Interceptor = Interceptor { chain ->
            val originalRequest: Request = chain.request()
            val requestBuilder: Request.Builder = originalRequest.newBuilder()
                .addHeader("Accept-Encoding", "gzip")
                .addHeader("Accept", "application/json")
                .addHeader("Content-Type", "application/json; charset=utf-8")
                .method(originalRequest.method(), originalRequest.body())
            requestBuilder.addHeader(
                "Token", AppConfig.Token.get()
            ) //添加请求头信息，服务器进行token有效性验证
            val request: Request = requestBuilder.build()
            chain.proceed(request)
        }
    val okHttpClient = OkHttpClient.Builder()
        .callTimeout(20, TimeUnit.SECONDS)
//        .writeTimeout(10,TimeUnit.SECONDS)
//        .connectTimeout(10,TimeUnit.SECONDS)
        .addInterceptor(headerInterceptor)
        .build()

     val retrofit = Retrofit.Builder().baseUrl(HttpConstant.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
         .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .client(okHttpClient)
        .build()
    }
//    fun create(clazz: Class<T>):T{
//       return retrofit.create( clazz)
//    }
}