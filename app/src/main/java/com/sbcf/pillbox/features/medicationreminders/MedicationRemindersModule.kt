package com.sbcf.pillbox.features.medicationreminders

import android.content.Context
import com.sbcf.pillbox.data.AppDatabase
import com.sbcf.pillbox.features.medicationreminders.data.repositories.MedicationReminderDao
import com.sbcf.pillbox.features.medicationreminders.data.repositories.MedicationRemindersRepository
import com.sbcf.pillbox.features.medicationreminders.data.repositories.MedicationRemindersRepositoryImpl
import com.sbcf.pillbox.features.medicationreminders.services.MedicationAlarmScheduler
import com.sbcf.pillbox.features.medicationreminders.services.MedicationAlarmSchedulerImpl
import com.sbcf.pillbox.features.medicationreminders.services.MedicationReminderPublisher
import com.sbcf.pillbox.features.medicationreminders.services.MedicationReminderPublisherImpl
import com.sbcf.pillbox.features.medicationreminders.services.ReminderTimestampCalculator
import com.sbcf.pillbox.features.medicationreminders.services.ReminderTimestampCalculatorImpl
import com.sbcf.pillbox.utils.Clock
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MedicationRemindersModule {
    @Provides
    fun provideMedicationReminderDao(db: AppDatabase): MedicationReminderDao =
        db.medicationReminderDao()

    @Provides
    @Singleton
    fun provideMedicationRemindersRepository(dao: MedicationReminderDao): MedicationRemindersRepository =
        MedicationRemindersRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideMedicationsAlarmScheduler(
        @ApplicationContext context: Context,
        clock: Clock
    ): MedicationAlarmScheduler =
        MedicationAlarmSchedulerImpl(context, clock)

    @Provides
    @Singleton
    fun provideMedicationReminderPublisher(
        @ApplicationContext context: Context,
        clock: Clock,
        scheduler: MedicationAlarmScheduler,
        calculator: ReminderTimestampCalculator
    ): MedicationReminderPublisher =
        MedicationReminderPublisherImpl(context, clock, scheduler, calculator)

    @Provides
    @Singleton
    fun provideReminderTimestampCalculator(clock: Clock): ReminderTimestampCalculator =
        ReminderTimestampCalculatorImpl(clock)
}