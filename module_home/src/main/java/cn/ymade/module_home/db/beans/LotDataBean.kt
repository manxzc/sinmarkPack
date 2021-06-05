package cn.ymade.module_home.db.beans

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * @author zc.xie
 * @date 2021/6/4 0004.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
@Entity
data class LotDataBean(
    @PrimaryKey val LotSN:String,
    @ColumnInfo(name = "LotNo") var LotNo: String?,
    @ColumnInfo(name = "LotName") var LotName: String?,
    @ColumnInfo(name = "Stamp") var Stamp: Long?,
    @ColumnInfo(name = "Status") var Status: Int=1,  //默认为1 ，0表示删除
    @ColumnInfo(name = "upload") var upload: Int=0,   //默认为0  1 为上传
)
