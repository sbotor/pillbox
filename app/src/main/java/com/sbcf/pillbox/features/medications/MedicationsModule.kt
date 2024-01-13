package com.sbcf.pillbox.features.medications

import com.sbcf.pillbox.data.AppDatabase
import com.sbcf.pillbox.features.medications.data.MedicationDao
import com.sbcf.pillbox.features.medications.data.MedicationsRepository
import com.sbcf.pillbox.features.medications.data.MedicationsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class MedicationsModule {
    @Provides
    fun provideMedicationDao(db: AppDatabase) = db.medicationDao()

    @Provides
    fun provideMedicationsRepository(dao: MedicationDao): MedicationsRepository =
        MedicationsRepositoryImpl(dao)
}