package ru.freeezzzi.tinkoff_test_task.domain.repository

import ru.freeezzzi.tinkoff_test_task.domain.OperationResult
import ru.freeezzzi.tinkoff_test_task.domain.model.GifDTO

interface LoadGifRepository {
    suspend fun loadNextGif(): OperationResult<GifDTO, String?>

    suspend fun loadPrevGif(): OperationResult<GifDTO, String?>
}
