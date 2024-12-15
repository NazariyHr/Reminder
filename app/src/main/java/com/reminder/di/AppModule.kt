package com.reminder.di

import android.content.Context
import androidx.room.Room
import com.reminder.data.LocalDataBase
import com.reminder.data.TasksRepositoryImpl
import com.reminder.domain.loggers.Logger
import com.reminder.domain.loggers.SharedPreferencesLogger
import com.reminder.domain.repository.TasksRepository
import com.reminder.domain.scheduling.TaskReminderScheduler
import com.reminder.domain.scheduling.TaskReminderSchedulerImpl
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
    fun provideContext(
        @ApplicationContext context: Context
    ): Context {
        return context
    }

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

    @Provides
    @Singleton
    fun provideTaskReminderScheduler(
        context: Context,
        logger: Logger
    ): TaskReminderScheduler {
        return TaskReminderSchedulerImpl(
            context,
            logger
        )
    }

    @Provides
    @Singleton
    fun provideLogger(
        @ApplicationContext context: Context
    ): Logger {
        return SharedPreferencesLogger(context)
    }
}