package com.longkd.quizizz.di

import android.content.Context
import com.longkd.quizizz.data.prefs.PreferenceStorage
import com.longkd.quizizz.data.prefs.SharedPreferenceStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {

    @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
    @Singleton
    @Provides
    fun providePreferenceStorage(@ApplicationContext context: Context): PreferenceStorage =
        SharedPreferenceStorage(context)
}