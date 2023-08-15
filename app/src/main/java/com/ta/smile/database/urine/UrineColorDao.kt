package com.ta.smile.database.urine

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ta.smile.database.feces.color.FecesColorData

@Dao
interface UrineColorDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(urineColor: UrineColorData)

    @Delete
    fun delete(urineColor: UrineColorData)

//    @Query("SELECT * FROM feces_warna LIMIT -1 OFFSET 14")
//    fun getFecesColorData(): LiveData<List<FecesColorData>>

//    @Query("SELECT * FROM feces_warna")
//    fun getFecesColorData(): LiveData<List<FecesColorData>>

    @Query("SELECT * FROM urine_warna ORDER BY id DESC LIMIT 21")
    fun getUrineColorData(): LiveData<List<UrineColorData>>

    @Query("SELECT * FROM urine_warna ORDER BY id DESC LIMIT 1")
    fun getLatestUrineColorData(): LiveData<UrineColorData>

    @Query("SELECT * FROM urine_warna ORDER BY id DESC LIMIT 1")
    fun getLatestDataUrineColorNL(): UrineColorData

    @Query("DELETE FROM urine_warna WHERE createdAt = :createdAt")
    fun deleteInitialUc(createdAt: String)

}