package com.yilin.mybase.module

import android.app.Application
import com.yilin.mybase.MyApp
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class ApplicationModule {

    @Provides
    @ApplicationContext
    fun provideApplication(): Application {
        return MyApp.instance
    }
}