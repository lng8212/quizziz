package com.longkd.quizizz.di

import com.longkd.quizizz.ui.theme.ThemedActivityDelegate
import com.longkd.quizizz.ui.theme.ThemedActivityDelegateImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
abstract class ThemeModule {
    @Binds
    abstract fun bindThemeDelegates(theme: ThemedActivityDelegateImpl): ThemedActivityDelegate
}