package cn.ymade.module_home.db.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Update
import cn.ymade.module_home.db.beans.LotDataBean

/**
 * @author zc.xie
 * @date 2021/6/4 0004.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
@Dao
interface LotDao {
    @Query("SELECT * FROM LotDataBean where Status=1")
    fun getAll(): List<LotDataBean>

    @Query("SELECT * FROM LotDataBean where LotNo=:lotN and Status=1")
    fun getAllByLotNo(lotN:String): List<LotDataBean>

    @Query("SELECT * FROM LotDataBean where LotNo=:lotN and upload=:upStatus")
    fun getAllByLotNoAndStatus(lotN:String,upStatus:Int): List<LotDataBean>

    @Update
    fun updateLot(vararg bean: LotDataBean)

    @Delete
    fun deleteLot(vararg bean: LotDataBean)

    @Query("DELETE FROM LotDataBean ")
    fun deleteAll()
}