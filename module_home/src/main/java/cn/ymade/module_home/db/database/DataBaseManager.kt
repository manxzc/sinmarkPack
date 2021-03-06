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
    private val MIGRATIONS = arrayOf(Migration1,Migration2_3,Migration3_4)
    private lateinit var application: Application
    val db: AppDataBase by lazy {
        Room.databaseBuilder(application.applicationContext, AppDataBase::class.java, DB_NAME)
            .addMigrations(*MIGRATIONS)
            .addCallback(CreatedCallBack)
            .build()
    }
    fun closeAll(){
        Thread{ db.clearAllTables() }.start()
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
//            // 数据库的升级语句
//            database.execSQL("CREATE TABLE IF NOT EXISTS `depart` (`Depart` TEXT  NOT NULL,`current` INTEGER NOT NULL DEFAULT 0,  'Number' TEXT,PRIMARY KEY(`Depart`))")
//            database.execSQL("CREATE TABLE IF NOT EXISTS `staff` ('uid'  INTEGER PRIMARY KEY AUTOINCREMENT,`current` INTEGER NOT NULL DEFAULT 0, `Depart` TEXT, 'Staff' TEXT,'Phone' TEXT)")

        }
    }

    private object Migration2_3 : Migration(2, 3) {
        override fun migrate(database: SupportSQLiteDatabase) {
//            // 数据库的升级语句
//            database.execSQL("CREATE TABLE IF NOT EXISTS `LotDataBean` (`LotSN` TEXT  NOT NULL,`items`INTEGER NOT NULL DEFAULT 0,`isLocal`INTEGER NOT NULL DEFAULT 0,`LotNo` TEXT,`LotName` TEXT,`Stamp` INTEGER NOT NULL DEFAULT 0,`Status` INTEGER NOT NULL DEFAULT 1,`upload` INTEGER NOT NULL DEFAULT 0,  'LotNo' TEXT,PRIMARY KEY(`LotSN`))")
//            database.execSQL("CREATE TABLE IF NOT EXISTS `SNBean` ('SN'  TEXT NOT NULL , `NO` INTEGER NOT NULL DEFAULT 0,`out` INTEGER NOT NULL DEFAULT 0 ,`Title` TEXT, 'LotSN' TEXT,'Phone' TEXT,`ModifyTime` Text,`Stamp` Status  NOT NULL DEFAULT 1,`upload` INTEGER NOT NULL DEFAULT 0,PRIMARY KEY(`SN`))")
        }
    }
    private object Migration3_4 : Migration(3, 4) {
        override fun migrate(database: SupportSQLiteDatabase) {
//            // 数据库的升级语句
//            database.execSQL("ALTER TABLE LotDataBean  ADD COLUMN staff TEXT")
        }
    }

}