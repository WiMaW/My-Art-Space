package com.wioletamwrobel.myartspace.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "album")
class Album(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String = "",
    val description: String = "",
    val image: String = "",
    @ColumnInfo(name = "create_date") val createDate: String = ""
) {

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
