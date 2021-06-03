package cn.ymade.module_home.db.database

import androidx.room.Database
import androidx.room.DatabaseConfiguration
import androidx.room.InvalidationTracker
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteOpenHelper
import cn.ymade.module_home.db.beans.DepartBean
import cn.ymade.module_home.db.beans.DevInfoBean
import cn.ymade.module_home.db.beans.StaffBean
import cn.ymade.module_home.db.dao.DepartStaffDao
import cn.ymade.module_home.db.dao.DevInfoDao

/**
 * @author zc.xie
 * @date 2021/6/1 0001.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
@Database(entities = [DevInfoBean::class,DepartBean::class,StaffBean::class], version = 2 ,exportSchema = false)
abstract class AppDataBase :RoomDatabase(){
    abstract fun devinfoDao(): DevInfoDao
    abstract fun departStaffDao(): DepartStaffDao
}