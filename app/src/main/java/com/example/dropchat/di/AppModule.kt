package com.example.dropchat.di

import com.example.dropchat.domainLayer.remote.ServerRepo
import com.example.dropchat.domainLayer.remote.ServerRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideServerRepo() : ServerRepo {
        return ServerRepoImpl()
    }



}