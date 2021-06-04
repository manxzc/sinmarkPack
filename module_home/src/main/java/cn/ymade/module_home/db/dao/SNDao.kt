package cn.ymade.module_home.db.dao

import androidx.room.*
import cn.ymade.module_home.db.beans.SNBean

/**
 * @author zc.xie
 * @date 2021/6/4 0004.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
@Dao
interface SNDao {
    @Query("SELECT * FROM SNBean  ")
    fun getAll(): List<SNBean>

    @Query("SELECT * FROM SNBean where out=:statusCode")
    fun getAllIiOut(statusCode: Int): List<SNBean>

    @Query("SELECT COUNT(*) FROM SNBean WHERE out=:statusCode")
    fun getAllInOutContBy(statusCode: Int):Int

    @Query("SELECT COUNT(*) FROM SNBean WHERE ModifyTime=:day_ymd and out=1")
    fun getAllOutContBy(day_ymd:String):Int

    @Query("SELECT COUNT(*) FROM SNBean WHERE upload=:uploadStatus ")
    fun getWaitContBy(uploadStatus:Int):Int

    @Query("SELECT * FROM SNBean where LotNo=:lotN and Status=1")
    fun getAllByLotNo(lotN:String): List<SNBean>

    @Query("SELECT * FROM SNBean where LotNo=:lotN and upload=:uploadCode")
    fun getAllByLotNoAndUpcode(lotN:String,uploadCode:Int): List<SNBean>

    @Update
    fun updateSN(vararg bean:SNBean)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insertList( beans:List<SNBean>)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg bean:SNBean)

    @Delete
    fun delete(vararg bean:SNBean)

    @Query("DELETE FROM SNBean ")
    fun deleteAll()

}