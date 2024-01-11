package com.sbcf.pillbox

import android.content.Context
import androidx.room.Room
import com.sbcf.pillbox.data.AppDatabase
import com.sbcf.pillbox.features.medications.data.MedicationDao
import com.sbcf.pillbox.features.medications.data.MedicationsRepository
import com.sbcf.pillbox.features.medications.data.MedicationsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Provides
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, "database").build()

    @Provides
    fun provideMedicationDao(db: AppDatabase) = db.medicationDao()

    @Provides
    fun provideMedicationsRepository(dao: MedicationDao): MedicationsRepository = MedicationsRepositoryImpl(dao)
}