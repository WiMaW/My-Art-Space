package com.wioletamwrobel.myartspace.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "art")
class Art (
    @ColumnInfo(name = "art_id") @PrimaryKey(autoGenerate = true) val artId: Long = 0,
    val image: String = "",
    //@ColumnInfo(name = "content_description") val contentDescription: String? = null,
    val title: String = "",
    val method: String = "",
    val date: String = "",
    val albumId: Long = 0
)