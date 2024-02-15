package com.wioletamwrobel.myartspace.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName = "art")
class Art (artId: Long = 0, image: String = "", title: String = "", method: String = "", date: String = "", albumId: Long = 0){
    @ColumnInfo(name = "art_id")
    @PrimaryKey(autoGenerate = true)
    val artId: Long = artId
    val image: String = image
    //@ColumnInfo(name = "content_description") val contentDescription: String? = null,
    val title: String = title
    val method: String = method
    val date: String = date
    val albumId: Long = albumId
}