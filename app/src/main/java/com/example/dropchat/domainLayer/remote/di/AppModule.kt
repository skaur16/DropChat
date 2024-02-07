package com.example.dropchat.domainLayer.remote.di

import com.example.dropchat.domainLayer.remote.ServerRepo
import com.example.dropchat.domainLayer.remote.ServerRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import javax.inject.Singleton

@Module
@InstallIn(Singleton::class)
object AppModule {

    @Singleton
    @Provides
    fun provideServerRepo() : ServerRepo {
        return ServerRepoImpl()
    }



}