package com.example.e_book.di

import com.example.e_book.repo.repo
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object HiltModule {



    @Provides
    @Singleton
    fun provideFirebaseRealtimeDatabase(): FirebaseDatabase{
        return FirebaseDatabase.getInstance()

    }

    @Provides
    @Singleton
    fun provideRepo(firebaseDatabase: FirebaseDatabase):repo{
        return repo(firebaseDatabase)
    }

}