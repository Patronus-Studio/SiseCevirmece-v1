package com.patronusstudio.sisecevirmece.abstracts

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.patronusstudio.sisecevirmece.interfaces.DogrulukDao
import com.patronusstudio.sisecevirmece.model.DogrulukModel

@Database(entities = [DogrulukModel::class], version = 4)
abstract class DogrulukDatabase : RoomDatabase() {

    abstract fun dogrulukDao(): DogrulukDao

    companion object {

        private var dogrulukDb: DogrulukDatabase? = null

        fun getDatabaseManager(mContext: Context): DogrulukDatabase {
            if (dogrulukDb == null) {
                dogrulukDb = Room.databaseBuilder(
                    mContext,
                    DogrulukDatabase::class.java,
                    "Dogruluk.db"
                )
                        //db güncellenirken eski verileri kaldır
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build()
            }
            return dogrulukDb!!
        }

    }

}