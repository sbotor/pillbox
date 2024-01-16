package com.sbcf.pillbox.features.medications

import android.content.Context
import com.sbcf.pillbox.data.AppDatabase
import com.sbcf.pillbox.features.medications.data.repositories.MedicationDao
import com.sbcf.pillbox.features.medicationreminders.data.repositories.MedicationReminderDao
import com.sbcf.pillbox.features.medicationreminders.data.repositories.MedicationRemindersRepository
import com.sbcf.pillbox.features.medicationreminders.data.repositories.MedicationRemindersRepositoryImpl
import com.sbcf.pillbox.features.medications.data.repositories.MedicationsRepository
import com.sbcf.pillbox.features.medications.data.repositories.MedicationsRepositoryImpl
import com.sbcf.pillbox.features.medicationreminders.services.MedicationAlarmScheduler
import com.sbcf.pillbox.features.medicationreminders.services.MedicationAlarmSchedulerImpl
import com.sbcf.pillbox.features.medicationreminders.services.MedicationReminderPublisher
import com.sbcf.pillbox.features.medicationreminders.services.MedicationReminderPublisherImpl
import com.sbcf.pillbox.utils.Clock
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
    fun provideMedicationDao(db: AppDatabase): MedicationDao = db.medicationDao()

    @Provides
    @Singleton
    fun provideMedicationsRepository(dao: MedicationDao): MedicationsRepository =
        MedicationsRepositoryImpl(dao)
}