package com.yilin.mybase.module

import com.yilin.mybase.database.MainRoom
import com.yilin.mybase.viewmodel.respository.MainLocalSource
import com.yilin.mybase.viewmodel.respository.MainRemoteSource
import com.yilin.mybase.viewmodel.respository.MainRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(room: MainRoom): MainRepository {
        return MainRepository(provideLocalSource(room), provideRemoteSource())
    }

    @Singleton
    @Provides
    fun provideRemoteSource(): MainRemoteSource {
        return MainRemoteSource()
    }

    @Singleton
    @Provides
    fun provideLocalSource(room: MainRoom): MainLocalSource {
        return MainLocalSource(room.getRoomDao())
    }

}