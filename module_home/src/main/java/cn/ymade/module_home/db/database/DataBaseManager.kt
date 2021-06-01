package cn.ymade.module_home.db.database

import android.app.Application
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * @author zc.xie
 * @date 2021/6/1 0001.
 * GitHub：
 * email：3104873490@qq.com
 * description：
 */
object DataBaseManager {
    private const val DB_NAME = "packData.db"
    private val MIGRATIONS = arrayOf(Migration1)
    private lateinit var application: Application
    val db: AppDataBase by lazy {
        Room.databaseBuilder(application.applicationContext, AppDataBase::class.java, DB_NAME)
            .addMigrations(*MIGRATIONS)
            .addCallback(CreatedCallBack)
            .build()
    }

    fun saveApplication(application: Application) {
        DataBaseManager.application = application
    }
    private object CreatedCallBack : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            //在新装app时会调用，调用时机为数据库build()之后，数据库升级时不调用此函数
            MIGRATIONS.map {
                it.migrate(db)
            }
        }
    }

    private object Migration1 : Migration(1, 2) {
        override fun migrate(database: SupportSQLiteDatabase) {
            // 数据库的升级语句
            // database.execSQL("")
        }
    }
}