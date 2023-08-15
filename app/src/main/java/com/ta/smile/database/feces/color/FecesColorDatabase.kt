package com.ta.smile.database.feces.color

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FecesColorData::class], version = 1,  exportSchema = true)
abstract class FecesColorDatabase: RoomDatabase() {
    abstract fun fecesColorDao(): FecesColorDao

    companion object{
        @Volatile
        private var INSTANCE: FecesColorDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FecesColorDatabase {
            if (INSTANCE == null){
                synchronized(FecesColorDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FecesColorDatabase::class.java, "feces_warna_database").createFromAsset("db/feces_warna.db").build()
//                        FecesColorDatabase::class.java, "feces_warna_database").build()
                }

            }
            return INSTANCE as FecesColorDatabase
        }
    }
}