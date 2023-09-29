package com.yilin.mybase.module

import com.google.gson.Gson
import com.yilin.mybase.MyApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GsonModule {

    @Singleton
    @Provides
    fun provideGson(): Gson {
        return MyApp.instance.gson
    }

}