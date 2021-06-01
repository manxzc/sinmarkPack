package cn.ymade.module_home.db.dao

import androidx.room.*
import cn.ymade.module_home.db.beans.DevInfoBean
import kotlinx.android.extensions.ContainerOptions

/**
 * @author zc.xie
 * @date 2021/6/1 0001.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
@Dao
interface DevInfoDao {
    @Query("SELECT * FROM devinfobean")
    fun getAll(): List<DevInfoBean>

    @Query("SELECT * FROM devinfobean WHERE Renew =(:renew) limit 1")
    fun loadAllByIds(renew:Int): DevInfoBean

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg devinfos: DevInfoBean)

    @Delete
    fun delete(devinfo: DevInfoBean)
}