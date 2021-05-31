package com.ymade.module_login.net

import com.ymade.module_login.LoginModel
import com.zcxie.zc.model_comm.net.HttpConstant
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LoginApi {
    @FormUrlEncoded
    @POST(HttpConstant.URL_PDA_RGS)
    fun  register(@Field("AppID") AppID: String,
                   @Field("Code") company: String,
                   @Field("SN") SN: String,
                   @Field("UUID") UUID: String,
                   @Field("Model") Model: String)  : Observable<LoginModel>
}