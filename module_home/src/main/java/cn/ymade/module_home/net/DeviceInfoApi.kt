package cn.ymade.module_home.net

import cn.ymade.module_home.model.DepartStaffInfo
import cn.ymade.module_home.model.DeviceInfo
import cn.ymade.module_home.model.GoodList
import com.zcxie.zc.model_comm.model.BaseModel
import com.zcxie.zc.model_comm.net.HttpConstant
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
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
//    fun  queryDvInfo(@Header("Token") token:String)  : Observable<Response<DeviceInfo?>>
    @POST(HttpConstant.URL_PDA_IDX)
    fun  queryDvInfo(@Header("token")  token:String)  : retrofit2.Call<DeviceInfo>

    @POST(HttpConstant.URL_PDA_STAFF)
    fun  queryDepartStaff(@Header("token")  token:String)  : retrofit2.Call<DepartStaffInfo>

    @FormUrlEncoded
    @POST(HttpConstant.URL_SCAN_Goods_List)
    fun  queryGoodList(@Header("token")  token:String,@Field("NextSN")NextSN:Int)  : retrofit2.Call<GoodList>

    @POST(HttpConstant.URL_SCAN_LOT_UP)
    fun  queryUpload(@Header("token")  token:String)  : retrofit2.Call<BaseModel>
}