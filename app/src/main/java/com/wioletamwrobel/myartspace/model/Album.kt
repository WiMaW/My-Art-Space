package com.wioletamwrobel.myartspace.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "album")
class Album (id: Long = 0, title: String = "", description: String = "", image:String = "", createDate: String = "" ) {
    @PrimaryKey(autoGenerate = true)
    val id: Long = id
    val title: String = title
    val description: String = description
    val image: String = image
    @ColumnInfo(name = "create_date")
    val createDate: String = createDate
    //@ColumnInfo(name = "art_number") val artNumber: Int = 0,

    fun doesMatchSearchQuery(query: String): Boolean {
        val matchingCombintions = listOf(
            title,
            createDate,
            "$title$createDate",
            "$createDate$title",
            "$title $createDate",
            "$createDate $title",
            "${title.first()}"
        )
        return matchingCombintions.any {
            it.contains(query, ignoreCase = true)
        }
    }
}

data class AlbumWithArts(
    @Embedded val album: Album,
    @Relation(
        parentColumn = "id",
        entityColumn = "albumId"
    )
    val artsList: List<Art>
)
