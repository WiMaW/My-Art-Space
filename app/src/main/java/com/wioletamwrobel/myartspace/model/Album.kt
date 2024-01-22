package com.wioletamwrobel.myartspace.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "album")
data class Album (
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val image: String = "",
    @ColumnInfo(name = "create_date") val createDate: String = "",
    @ColumnInfo(name = "art_number") val artNumber: Int = 0,
)

data class AlbumWithArts (
    @Embedded val album: Album,
    @Relation(
        parentColumn = "id",
        entityColumn = "albumId"
    )
    val artsList: List<Art>
)