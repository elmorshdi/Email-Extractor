package com.elmorshdi.extractor.di

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager
import androidx.room.Room
import com.elmorshdi.extractor.db.AlarmDao
import com.elmorshdi.extractor.db.Database
import com.elmorshdi.extractor.other.Constants.DATABASE_NAME
import com.elmorshdi.extractor.other.ManagePreferences
import com.elmorshdi.extractor.repository.MainRepository
import com.elmorshdi.extractor.ui.viewModels.AlarmViewModel
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Singleton
    @Provides
    fun provideRunningDatabase(
        @ApplicationContext app: Context
    ) = Room.databaseBuilder(
        app,
        Database::class.java,
        DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    @Singleton
    @Provides
    fun provideRunDao(db: Database) = db.getAlarmDao()
    @Singleton
    @Provides
    fun provideMainRepository( alarmDao : AlarmDao)=MainRepository(alarmDao)


    @Singleton
    @Provides
    fun provideAddViewModel( mainRepository: MainRepository)= AlarmViewModel(mainRepository)
    @Provides
    @Singleton
     fun provideMangePreference(preferences: SharedPreferences)= ManagePreferences(preferences)
    @Provides
    @Singleton
    fun providePreference(@ApplicationContext context: Context): SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(context)
    }

//    @Singleton
//    @Provides
//    fun provideName(sharedPref: SharedPreferences) = sharedPref.getString(KEY_NAME, "") ?: ""
//
//    @Singleton
//    @Provides
//    fun provideWeight(sharedPref: SharedPreferences) = sharedPref.getFloat(KEY_WEIGHT, 80f)

}