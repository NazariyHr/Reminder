package com.reminder.di

import android.content.Context
import androidx.room.Room
import com.reminder.data.LocalDataBase
import com.reminder.data.TasksRepositoryImpl
import com.reminder.domain.repository.TasksRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {
    @Provides
    @Singleton
    fun provideGifsDatabase(
        @ApplicationContext context: Context
    ): LocalDataBase {
        return Room.databaseBuilder(
            context,
            LocalDataBase::class.java,
            "tasks.db"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTasksRepository(
        localDataBase: LocalDataBase
    ): TasksRepository {
        return TasksRepositoryImpl(
            localDataBase
        )
    }
}