package com.enesk.todocompose.di

import com.enesk.todocompose.data.local.ToDoDao
import com.enesk.todocompose.data.repository.ToDoRepositoryImpl
import com.enesk.todocompose.domain.repository.ToDoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideToDoRepository(
        dao: ToDoDao
    ): ToDoRepository {
        return ToDoRepositoryImpl(dao)
    }
}