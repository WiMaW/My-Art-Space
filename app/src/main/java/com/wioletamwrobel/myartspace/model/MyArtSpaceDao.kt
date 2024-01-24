package com.wioletamwrobel.myartspace.model

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Update

@Dao
interface MyArtSpaceDao {
    //Album
    @Insert
    fun createAlbum(album: Album)

    @Query("SELECT * FROM album")
    fun getAllAlbums(): List<Album>

    @Update
    fun updateAlbum(album: Album)

    @Delete
    fun deleteAlbum(album: Album)

    //Art
    @Insert
    fun createArt(art: Art)

    @Query("SELECT * FROM art WHERE albumId = :query")
    fun getAllArts(query: Long): List<Art>

    @Update
    fun updateArt(art: Art)

    @Delete
    fun deleteArt(art: Art)

    //Relation
    @Transaction
    @Query("SELECT * FROM album")
    fun getAlbumWithArts(): List<AlbumWithArts>

}