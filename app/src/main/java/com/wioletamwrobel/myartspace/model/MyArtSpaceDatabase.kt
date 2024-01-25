package com.wioletamwrobel.myartspace.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Album::class, Art::class], version = 1)
abstract class MyArtSpaceDatabase : RoomDatabase() {

    abstract fun getMyAlbumDao() : MyArtSpaceDao
    abstract fun getMyArtDao() : MyArtDao

    companion object {

        @Volatile //thread safety
        private var DATABASE_INSTANCE: MyArtSpaceDatabase? = null

        fun getDatabase(context: Context): MyArtSpaceDatabase {

            //synchronized do not allow this same code to run on multiple threads
            return DATABASE_INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context,
                    MyArtSpaceDatabase::class.java,
                    "my_art_space_database"
                ).build()
                DATABASE_INSTANCE = instance
                instance
            }
        }
    }

}