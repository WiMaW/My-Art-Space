package com.wioletamwrobel.myartspace

import android.app.Application
import android.util.Log
import com.wioletamwrobel.myartspace.model.MyArtSpaceDatabase
import com.wioletamwrobel.myartspace.model.MyArtSpaceDatabaseRepository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module

class MyArtSpaceApplication {
    class MyArtSpaceApplication: Application() {

        override fun onCreate() {
            super.onCreate()
            Log.d("MyTasksApp", "Application onCreate()")

            //w module tworzymy obiekty które będziemy wykorzystywać w aplikacji i będą one tworzone przy starcie:
            // single - tworzony tylko jeden obiekt danego typu
            //factory - przy każdym odwoływaniu się do danego obiektu będzie tworzony nowy
            startKoin {
                androidContext(this@MyArtSpaceApplication)
                modules(
                    module {
                        single { MyArtSpaceDatabase.getDatabase(androidContext()) }
                        factory { MyArtSpaceDatabaseRepository(get()) }

                        viewModel { MyArtSpaceAppViewModel(get()) }
                    }
                )
            }
        }
    }
}