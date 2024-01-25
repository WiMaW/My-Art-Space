package com.wioletamwrobel.myartspace.model

class MyArtSpaceDatabaseRepository(
    private val db: MyArtSpaceDatabase
) {

    suspend fun getAllArtsFromCurrentAlbum(
        albumId: Long
    ): List<Art> {
        return db.getMyArtDao().getAllArtsFromCurrentAlbum(albumId)
    }
}