package com.ta.smile.database.urine

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "urine_warna", indices = [Index(value = ["createdAt"], unique = true)])
data class UrineColorData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "coklat")
    var coklat: Float? = 0f,

    @ColumnInfo(name = "kuning_gelap")
    var kuning_g: Float? = 0f,

    @ColumnInfo(name = "merah")
    var merah: Float? = 0f,

    @ColumnInfo(name = "transparan")
    var transparan: Float? = 0f,

    @ColumnInfo(name = "type")
    var type: Int? = 0,

    @ColumnInfo(name = "createdAt")
    var createdAt: String? = null
)