package com.ta.smile.database.urine

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [UrineColorData::class], version = 1,  exportSchema = true)
abstract class UrineColorDatabase: RoomDatabase() {
    abstract fun urineColorDao(): UrineColorDao

    companion object{
        @Volatile
        private var INSTANCE: UrineColorDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): UrineColorDatabase{
            if (INSTANCE == null){
                synchronized(UrineColorDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        UrineColorDatabase::class.java, "urine_warna_database").createFromAsset("db/urine_warna.db").build()
                }
            }
            return INSTANCE as UrineColorDatabase
        }
    }
}