package com.zcxie.zc.model_comm.net

class HttpConstant {
    companion object{
        const val BASE_URL = "http://pda.ymade.cn"
        //设备激活
        const val URL_PDA_RGS= "$BASE_URL/PDA_Device/Register"
        //设备资料
        const val URL_PDA_IDX= "$BASE_URL/PDA_Device/Index"
        //员工列表
        const val URL_PDA_STAFF= "$BASE_URL/PACK_Staff/Index"
        //出库单上传
        const val URL_SCAN_LOT_UP= "$BASE_URL/SCAN_Lot/Update"
        //出库单删除
        const val URL_SCAN_LOT_DEL= "$BASE_URL/SCAN_Lot/Update"

        //可用 条码
        const val URL_SCAN_Goods_List= "$BASE_URL//PACK_Goods/List"
    }
}