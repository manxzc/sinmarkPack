package cn.ymade.module_home.db.beans

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author zc.xie
 * @date 2021/6/3 0003.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
@Entity(tableName = "staff")
data class StaffBean(
    @PrimaryKey(autoGenerate = true) val uid:Int,
    @ColumnInfo(name = "Depart") val Depart: String?,
    @ColumnInfo(name = "Staff") val Staff: String?,
    @ColumnInfo(name = "current") var current: Int? =0,
    @ColumnInfo(name = "Phone") val Phone: String?

)
