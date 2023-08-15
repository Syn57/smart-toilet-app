package com.ta.smile.database.feces.form

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [FecesFormData::class], version = 1,  exportSchema = true)
abstract class FecesFormDatabase: RoomDatabase() {
    abstract fun fecesFormDao(): FecesFormDao

    companion object{
        @Volatile
        private var INSTANCE: FecesFormDatabase? = null

        @JvmStatic
        fun getDatabase(context: Context): FecesFormDatabase {
            if (INSTANCE == null){
                synchronized(FecesFormDatabase::class.java){
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                        FecesFormDatabase::class.java, "feces_bentuk_database").createFromAsset("db/feces_bentuk.db").build()
//                        FecesFormDatabase::class.java, "feces_bentuk_database").build()
                }
            }
            return INSTANCE as FecesFormDatabase
        }
    }
}