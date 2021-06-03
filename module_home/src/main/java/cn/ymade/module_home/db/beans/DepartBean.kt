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
@Entity(tableName = "depart")
data class DepartBean(
    @PrimaryKey val Depart:String,
    @ColumnInfo(name = "current") var current: Int =0,
    @ColumnInfo(name = "Number") val Number: String?
    )
