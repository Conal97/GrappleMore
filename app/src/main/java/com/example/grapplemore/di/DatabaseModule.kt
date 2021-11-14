package com.example.grapplemore.di

import android.content.Context
import androidx.room.Room
import com.example.grapplemore.data.model.AppDatabase
import com.example.grapplemore.utils.Constants.APP_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    // 'Manual' for dagger-hilt to build and inject the database
    // Singleton so we only have one instance of the database
    @Singleton
    @Provides
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        APP_DATABASE_NAME
    ).fallbackToDestructiveMigration().build()

    // Dao's -> dagger allows us to access these based on the database we provided

    @Singleton
    @Provides
    fun providesUserProfileDao(db: AppDatabase) = db.getUserProfileDao()

}