package com.ta.smile.database.feces.form

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName = "feces_bentuk", indices = [Index(value = ["createdAt"], unique = true)])
data class FecesFormData (
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = 0,

    @ColumnInfo(name = "tipe1")
    var tipe1: Float? = 0f,

    @ColumnInfo(name = "tipe2")
    var tipe2: Float? = 0f,

    @ColumnInfo(name = "tipe3")
    var tipe3: Float? = 0f,

    @ColumnInfo(name = "tipe4")
    var tipe4: Float? = 0f,

    @ColumnInfo(name = "tipe5")
    var tipe5: Float? = 0f,

    @ColumnInfo(name = "tipe6")
    var tipe6: Float? = 0f,

    @ColumnInfo(name = "tipe7")
    var tipe7: Float? = 0f,

    @ColumnInfo(name = "type")
    var type: Int?= 0,

    @ColumnInfo(name = "createdAt")
    var createdAt: String? = null
)