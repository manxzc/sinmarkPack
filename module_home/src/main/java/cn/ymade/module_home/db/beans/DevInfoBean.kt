package cn.ymade.module_home.db.beans

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author zc.xie
 * @date 2021/6/1 0001.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
@Entity
data class DevInfoBean(
    @PrimaryKey val uid:String,
    @ColumnInfo(name = "UUID") val UUID: String?,
    @ColumnInfo(name = "Company") val Company: String?,
    @ColumnInfo(name = "CompanySN") val CompanySN: String?,
    @ColumnInfo(name = "Device") val Device: String?,
    @ColumnInfo(name = "DeviceSN") val DeviceSN: String?,
    @ColumnInfo(name = "ExpiryDate") val ExpiryDate: String?,
    @ColumnInfo(name = "RegDate") val RegDate: String?,
    @ColumnInfo(name = "SN") val SN: String?,
    @ColumnInfo(name = "Renew") val Renew: Int?,
    @ColumnInfo(name = "Url") val Url: String?,
    @ColumnInfo(name = "Version") val Version: String?
)