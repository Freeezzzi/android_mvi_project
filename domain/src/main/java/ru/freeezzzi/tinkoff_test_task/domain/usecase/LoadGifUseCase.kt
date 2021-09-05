package ru.freeezzzi.tinkoff_test_task.domain.usecase

import ru.freeezzzi.tinkoff_test_task.domain.OperationResult
import ru.freeezzzi.tinkoff_test_task.domain.model.GifDTO

interface LoadGifUseCase {

    suspend fun load(): OperationResult<GifDTO, String?>
}
