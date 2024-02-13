package com.wioletamwrobel.myartspace.model

class MyArtSpaceDatabaseRepository(
    private val db: MyArtSpaceDatabase
) {

    suspend fun getAllAlbums(): List<Album> {
        return db.getMyAlbumDao().getAllAlbums()
    }
}