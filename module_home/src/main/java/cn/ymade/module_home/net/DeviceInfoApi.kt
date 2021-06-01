package cn.ymade.module_home.net

import cn.ymade.module_home.model.DeviceInfo
import com.zcxie.zc.model_comm.net.HttpConstant
import io.reactivex.rxjava3.core.Observable
import okhttp3.Call
import okhttp3.Response
import org.json.JSONObject
import retrofit2.http.*

/**
 * @author zc.xie
 * @date 2021/6/1 0001.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
interface DeviceInfoApi {
//    @FormUrlEncoded
//    @GET(HttpConstant.URL_PDA_IDX)
//    fun  queryDvInfo(@Header("Token") token:String)  : Observable<String>
    @POST(HttpConstant.URL_PDA_IDX)
    fun  queryDvInfo(@Header("token")  token:String)  :retrofit2.Call<DeviceInfo>
}