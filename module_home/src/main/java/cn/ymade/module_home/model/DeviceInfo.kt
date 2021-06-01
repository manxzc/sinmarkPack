package cn.ymade.module_home.model

/**
 * @author zc.xie
 * @date 2021/6/1 0001.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
data class DeviceInfo(
    val Device: List<Device>,
    val Version: List<Version>,
    val code: Int
)

data class Device(
    val Company: String,
    val CompanySN: String,
    val Device: String,
    val DeviceSN: String,
    val ExpiryDate: String,
    val RegDate: String,
    val SN: String,
    val UUID: String
)

data class Version(
    val Renew: Int,
    val Url: String,
    val Version: String
)