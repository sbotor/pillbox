package com.sbcf.pillbox.features.medications

import android.content.Context
import com.sbcf.pillbox.data.AppDatabase
import com.sbcf.pillbox.features.medications.data.MedicationDao
import com.sbcf.pillbox.features.medications.data.MedicationNotificationsRepository
import com.sbcf.pillbox.features.medications.data.MedicationNotificationsRepositoryImpl
import com.sbcf.pillbox.features.medications.data.MedicationsRepository
import com.sbcf.pillbox.features.medications.data.MedicationsRepositoryImpl
import com.sbcf.pillbox.features.medications.services.MedicationAlarmScheduler
import com.sbcf.pillbox.features.medications.services.MedicationAlarmSchedulerImpl
import com.sbcf.pillbox.services.Clock
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class MedicationsModule {
    @Provides
    fun provideMedicationDao(db: AppDatabase) = db.medicationDao()

    @Provides
    @Singleton
    fun provideMedicationsRepository(dao: MedicationDao): MedicationsRepository =
        MedicationsRepositoryImpl(dao)

    @Provides
    @Singleton
    fun provideMedicationNotificationsRepository(clock: Clock): MedicationNotificationsRepository =
        MedicationNotificationsRepositoryImpl(clock)

    @Provides
    @Singleton
    fun provideMedicationsNotificationScheduler(
        @ApplicationContext context: Context,
        clock: Clock
    ): MedicationAlarmScheduler =
        MedicationAlarmSchedulerImpl(context, clock)
}