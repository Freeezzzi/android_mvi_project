package ru.freeezzzi.tinkoff_test_task.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.freeezzzi.tinkoff_test_task.domain.usecase.*
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCaseModule {

    @Binds
    @Named("LoadPrevUseCase")
    abstract fun bindLoadPrevUseCase(loadPrevUseCaseImpl: LoadPrevUseCaseImpl): LoadGifUseCase

    @Binds
    @Named("LoadNextUseCase")
    abstract fun bindLoadNextUseCase(loadNextUseCaseImpl: LoadNextUseCaseImpl): LoadGifUseCase
}
