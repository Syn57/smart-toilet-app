package com.ta.smile.database.feces.color

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FecesColorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(fecesColor: FecesColorData)

    @Delete
    fun delete(fecesColor: FecesColorData)

//    @Query("SELECT * FROM feces_warna LIMIT -1 OFFSET 14")
//    fun getFecesColorData(): LiveData<List<FecesColorData>>

//    @Query("SELECT * FROM feces_warna")
//    fun getFecesColorData(): LiveData<List<FecesColorData>>

    @Query("SELECT * FROM feces_warna ORDER BY id DESC LIMIT 14")
    fun getFecesColorData(): LiveData<List<FecesColorData>>

    @Query("SELECT * FROM feces_warna ORDER BY id DESC LIMIT 1")
    fun getLatestData(): LiveData<FecesColorData>

    @Query("SELECT * FROM feces_warna ORDER BY id DESC LIMIT 1")
    fun getLatestDataNL(): FecesColorData

    @Query("SELECT (SELECT COUNT(*) FROM feces_warna) == 0")
    fun isEmpty(): LiveData<Boolean>

    @Query("DELETE FROM feces_warna WHERE createdAt = :createdAt")
    fun deleteInitialFc(createdAt: String)
}