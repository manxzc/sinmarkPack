package cn.ymade.module_home.db.dao

import androidx.room.*
import cn.ymade.module_home.db.beans.SNDeleteByLotBean

/**
 * @author zc.xie
 * @date 2021/6/6 0006.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
@Dao
interface DeleteSnDao {
    @Query("SELECT * FROM SNDeleteByLotBean  ")
    fun getAll(): List<SNDeleteByLotBean>

    @Query("SELECT * FROM SNDeleteByLotBean  where id=:id")
    fun getById(id:Int): List<SNDeleteByLotBean>

    @Query("SELECT * FROM SNDeleteByLotBean where SN==:sn")
    fun searchNotDeleteBySn(sn:String): List<SNDeleteByLotBean>


    @Query("SELECT * FROM SNDeleteByLotBean where LotSN=:lotSN ")
    fun getAllByLotSN(lotSN:String): List<SNDeleteByLotBean>

    @Query("SELECT * FROM SNDeleteByLotBean where SN==:sn and LotSN=:lotSN")
    fun searchNotDeleteBySnAndLotSN(sn:String,lotSN:String): List<SNDeleteByLotBean>

//    @Query("DELETE  FROM SNDeleteByLotBean where SN==:sn and LotSN=:lotSN")
//    fun deleteBySnAndLotSN(sn:String,lotSN:String)

//
//    @Query("DELETE FROM SNDeleteByLotBean WHERE (SN==:sn and LotSN=:lotSN)")
//    fun delete(sn:String,lotSN:String)

    @Update
    fun updateSN(vararg bean:SNDeleteByLotBean)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertList( beans:List<SNDeleteByLotBean>)

    @Insert (onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg bean:SNDeleteByLotBean)

    @Delete
    fun delete(vararg bean:SNDeleteByLotBean)

    @Delete
    fun deleteList( bean:List<SNDeleteByLotBean>)

    @Query("DELETE FROM SNDeleteByLotBean ")
    fun deleteAll()
}