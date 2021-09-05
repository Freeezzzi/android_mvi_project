package ru.freeezzzi.tinkoff_test_task.domain.usecase

import ru.freeezzzi.tinkoff_test_task.domain.OperationResult
import ru.freeezzzi.tinkoff_test_task.domain.model.GifDTO
import ru.freeezzzi.tinkoff_test_task.domain.repository.LoadGifRepository
import javax.inject.Inject

class LoadPrevUseCaseImpl @Inject constructor(
    private val loadGifRepository: LoadGifRepository,
) : LoadGifUseCase {
    override suspend fun load(): OperationResult<GifDTO, String?> = loadGifRepository.loadPrevGif()
}
