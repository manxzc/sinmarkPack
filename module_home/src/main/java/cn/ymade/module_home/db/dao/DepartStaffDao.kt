package cn.ymade.module_home.db.dao

import androidx.room.*
import cn.ymade.module_home.db.beans.DepartBean
import cn.ymade.module_home.db.beans.StaffBean

/**
 * @author zc.xie
 * @date 2021/6/3 0003.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
@Dao
interface DepartStaffDao {
    @Query("SELECT * FROM depart")
    fun getAllDepart(): List<DepartBean>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListDepart(beanList: List<DepartBean?>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertDepart(vararg entities: DepartBean?)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateDepart(entity: DepartBean?): Int

    @Delete
    fun deleteDepart(vararg entities: DepartBean?)

    @Query("DELETE FROM depart ")
    fun deleteAllDepart()


    @Query("SELECT * FROM staff where Depart=:depart")
    fun getAllStaffByDepart( depart:String): List<StaffBean>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateStaff(entity: StaffBean?): Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertListStaff(beanList: List<StaffBean?>?)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertStaff(vararg entities: StaffBean?)

    @Delete
    fun deleteStaff(vararg entities: StaffBean?)

    @Query("DELETE FROM staff ")
    fun deleteAllStaff()

    @Query("update depart set current=0")
    fun updateClearCurrentDepart()

    @Query("update depart set current=1 where depart=:depart ")
    fun updateCurrentDepart(depart:String)

    @Query("update staff set current=0")
    fun updateClearCurrentstaff()

    @Query("update staff set current=1 where staff=:staff")
    fun updateCurrentStaff(staff:String)
}