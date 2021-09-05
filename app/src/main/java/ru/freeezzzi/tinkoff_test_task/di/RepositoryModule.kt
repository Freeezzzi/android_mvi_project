package ru.freeezzzi.tinkoff_test_task.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.freeezzzi.tinkoff_test_task.data.repository.LoadGifRepositoryImpl
import ru.freeezzzi.tinkoff_test_task.domain.repository.LoadGifRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindLoadGifrepository(loadGifRepositoryImpl: LoadGifRepositoryImpl): LoadGifRepository
}
