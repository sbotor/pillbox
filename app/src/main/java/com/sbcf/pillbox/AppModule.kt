package com.sbcf.pillbox

import android.content.Context
import androidx.room.Room
import com.sbcf.pillbox.data.AppDatabase
import com.sbcf.pillbox.utils.Clock
import com.sbcf.pillbox.utils.ClockImpl
import com.sbcf.pillbox.utils.DisplayFormatter
import com.sbcf.pillbox.utils.DisplayFormatterImpl
import com.sbcf.pillbox.utils.validation.InputValidators
import com.sbcf.pillbox.utils.validation.InputValidatorsImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {
    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.DEFAULT_NAME).build()

    @Provides
    @Singleton
    fun provideInputValidators(@ApplicationContext context: Context): InputValidators =
        InputValidatorsImpl(context)

    @Provides
    @Singleton
    fun provideClock(): Clock = ClockImpl()

    @Provides
    @Singleton
    fun provideDisplayFormatter(@ApplicationContext context: Context): DisplayFormatter = DisplayFormatterImpl(context)
}