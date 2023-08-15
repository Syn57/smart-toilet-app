package com.ta.smile.database.feces.form

import androidx.lifecycle.LiveData
import androidx.room.*
import com.ta.smile.database.feces.color.FecesColorData

@Dao
interface FecesFormDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(fecesForm: FecesFormData)

    @Delete
    fun delete(fecesForm: FecesFormData)

//    @Query("SELECT * FROM feces_warna LIMIT -1 OFFSET 14")
//    fun getFecesColorData(): LiveData<List<FecesColorData>>

//    @Query("SELECT * FROM feces_warna")
//    fun getFecesColorData(): LiveData<List<FecesColorData>>

    @Query("SELECT * FROM feces_bentuk ORDER BY id DESC LIMIT 14")
    fun getFecesFormData(): LiveData<List<FecesFormData>>

    @Query("SELECT * FROM feces_bentuk ORDER BY id DESC LIMIT 1")
    fun getLatestFecesFormData(): LiveData<FecesFormData>

    @Query("SELECT * FROM feces_bentuk ORDER BY id DESC LIMIT 1")
    fun getLatestDataFecesFormNL(): FecesFormData

    @Query("DELETE FROM feces_bentuk WHERE createdAt = :createdAt")
    fun deleteInitialFc(createdAt: String)
}