package cn.ymade.module_home.model

import java.io.Serializable

/**
 * @author zc.xie
 * @date 2021/6/6 0006.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
class UploadLotbean :Serializable {
    var LotSN:String=""
    var LotNo:String=""
    var LotName:String=""
    var Stamp:String=""
    var Status:String=""
    var Param:List<UploadSNBean>?=null
}