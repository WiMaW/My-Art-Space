package com.wioletamwrobel.myartspace.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Relation
import androidx.room.Transaction
import androidx.room.Update

//Album
@Dao
interface MyArtSpaceDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createAlbum(album: Album)

    @Query("SELECT * FROM album")
    fun getAllAlbums(): List<Album>

    @Query("SELECT * FROM album WHERE id = :albumId")
    fun getAlbumById(albumId: Long): Album

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateAlbum(album: Album)

    @Delete
    fun deleteAlbum(album: Album)
}

//Art
@Dao
interface MyArtDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createArt(art: Art)

    @Query("SELECT * FROM art")
    fun getAllArts(): List<Art>

    @Query("SELECT * FROM art WHERE albumId = :albumId")
    fun getAllArtsFromCurrentAlbum(albumId: Long): List<Art>

    @Update(onConflict = OnConflictStrategy.REPLACE)
    fun updateArt(art: Art)

    @Delete
    fun deleteArt(art: Art)

    @Relation(entityColumn = "albumId", parentColumn = "id")
    @Transaction
    @Query("SELECT * FROM art WHERE albumId = :albumId")
    fun getAlbumWithArts(albumId: Long): List<Art>
}


