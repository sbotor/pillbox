package com.sbcf.pillbox.features.reminderhistory

import com.sbcf.pillbox.data.AppDatabase
import com.sbcf.pillbox.features.reminderhistory.data.repositories.ReminderHistoryDao
import com.sbcf.pillbox.features.reminderhistory.data.repositories.ReminderHistoryRepository
import com.sbcf.pillbox.features.reminderhistory.data.repositories.ReminderHistoryRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ReminderHistoryModule {
    @Provides
    fun provideReminderHistoryDao(db: AppDatabase): ReminderHistoryDao =
        db.medicationReminderHistoryDao()

    @Provides
    @Singleton
    fun provideReminderHistoryRepository(dao: ReminderHistoryDao): ReminderHistoryRepository =
        ReminderHistoryRepositoryImpl(dao)
}