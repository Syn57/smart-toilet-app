package com.ta.smile.database.feces.color

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "feces_warna", indices = [Index(value = ["createdAt"], unique = true)])
data class FecesColorData(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int =0,

    @ColumnInfo(name = "kehitaman")
    var kehitaman: Float? = 0f,

    @ColumnInfo(name = "kemerahan")
    var kemerahan: Float? = 0f,

    @ColumnInfo(name = "normal")
    var normal: Float?= 0f,

    @ColumnInfo(name = "pucat")
    var pucat: Float? = 0f,

    @ColumnInfo(name = "type")
    var type: Int? = 0,

    @ColumnInfo(name = "createdAt")
    var createdAt: String? = null
)
