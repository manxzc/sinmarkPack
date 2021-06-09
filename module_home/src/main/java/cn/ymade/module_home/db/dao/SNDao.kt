package cn.ymade.module_home.db.dao

import android.database.Cursor
import androidx.room.*
import cn.ymade.module_home.db.beans.SNBean
import cn.ymade.module_home.model.SNTitleBean

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

    @Query("SELECT * FROM SNBean where Status=1 ")
    fun getAllNotDelete(): List<SNBean>

    @Query("SELECT DISTINCT Title FROM SNBean where Status=1 ")
    fun getAllDifTitleNotDelete(): List<String>

    @Query("SELECT DISTINCT Title FROM SNBean where Status=1 and Title like '%'|| :title|| '%'")
    fun getAllDifTitleNotDeleteByTitle(title: String): List<String>

    @Query("SELECT * FROM SNBean where Status=1 and Title=:title")
    fun getAllNotDeleteByTitle(title: String): List<SNBean>

    @Query("SELECT * FROM SNBean where Status=1 and SN==:sn   limit 1")
    fun searchsingleNotDeleteBySn(sn:String): List<SNBean>

    @Query("SELECT * FROM SNBean where Status=1 and SN==:sn ")
    fun searchAllNotDeleteByMatchSN(sn:String): List<SNBean>

    @Query("SELECT * FROM SNBean where Status=1 and (SN==:sn or SN like '%'|| :sn|| '%')")
    fun searchAllNotDeleteBy(sn:String): List<SNBean>


    @Query("SELECT * FROM SNBean where Title=:title and out=:statusCode and Status=1 " )
    fun getAllInOut(title: String,statusCode: Int): List<SNBean>

    @Query("SELECT * FROM SNBean where  out=:statusCode and Status=1 and (Title==:key or Title like '%'|| :key|| '%')")
    fun searchAllInOut(statusCode: Int,key:String): List<SNBean>

    @Query("SELECT COUNT(*) FROM SNBean WHERE out=:statusCode")
    fun getAllInOutContBy(statusCode: Int):Int

    @Query("SELECT COUNT(*) FROM SNBean WHERE ModifyTime=:day_ymd and out=1")
    fun getAllOutContBy(day_ymd:String):Int

    @Query("SELECT COUNT(*) FROM SNBean WHERE upload=:uploadStatus ")
    fun getWaitContBy(uploadStatus:Int):Int

    @Query("SELECT * FROM SNBean where LotSN=:lotSN and Status=1")
    fun getAllByLotSN(lotSN:String): List<SNBean>


    @Query("SELECT * FROM SNBean where LotSN=:lotSN and upload=:uploadCode and Status=1")
    fun getAllByLotSNAndUpcode(lotSN:String,uploadCode:Int): List<SNBean>

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

    @Query("SELECT count(DISTINCT Title) FROM SNBean where out=:statusCode  and (ModifyTime) BETWEEN :startTime and :stopTime")
    fun getTitleNum(statusCode: Int,startTime:String ,stopTime:String):Int

    @Query("SELECT count(*) FROM SNBean where out=:statusCode and (ModifyTime) BETWEEN :startTime and :stopTime")
    fun getTimeAll(statusCode: Int,startTime:String ,stopTime:String):Int

    @Query("SELECT DISTINCT Title FROM SNBean where LotSN=:lotSn and Status=1")   //SNTitleBean
    fun getAllTitleBeanByLotSN(lotSn: String):List<String>

    @Query("SELECT *  FROM SNBean where LotSN=:lotSn and Title=:title and Status=1")   //
    fun getTitleBeanByLotSN(lotSn: String,title:String):  List<SNBean>

//    @Query("SELECT *  FROM SNBean where Title in (SELECT  DISTINCT Title  FROM SNBean )and LotSN=:lotSn  and Status=1 ")   //
//    fun testSubQuery(lotSn: String):  List<SNBean>
    @Query("SELECT *  FROM SNBean where Title in (SELECT  DISTINCT Title  FROM SNBean )and LotSN=:lotSn  and Status=1 ")   //
    fun testSubQueryCursor(lotSn: String):  Cursor

    @Query("SELECT *  FROM SNBean where Title in (SELECT  DISTINCT Title  FROM SNBean )and LotSN=:lotSn  and Status=1 ")   //
    fun testSubQuery(lotSn: String):  List<SNBean>

    @Query("SELECT *  FROM SNBean where Title in (SELECT  DISTINCT Title  FROM SNBean )and LotSN=:lotSn  and Status=1 ")   //
    fun queryTitle(lotSn: String):  List<SNBean>
}