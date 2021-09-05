package ru.freeezzzi.tinkoff_test_task.data.repository

import ru.freeezzzi.tinkoff_test_task.data.local.CashedGifs
import ru.freeezzzi.tinkoff_test_task.data.network.API
import ru.freeezzzi.tinkoff_test_task.data.network.models.toGifDTO
import ru.freeezzzi.tinkoff_test_task.domain.OperationResult
import ru.freeezzzi.tinkoff_test_task.domain.model.GifDTO
import ru.freeezzzi.tinkoff_test_task.domain.repository.LoadGifRepository
import javax.inject.Inject

class LoadGifRepositoryImpl @Inject constructor(
    private val api: API,
) : LoadGifRepository {
    private val cashed: CashedGifs get() = CashedGifs
    override suspend fun loadNextGif(): OperationResult<GifDTO, String?> =
        if (cashed.isLast) { // из сети
            try {
                val result = api.getRandomGif().toGifDTO(cashed.isFirstlyAdded)
                cashed.saveNewGif(result)
                OperationResult.Success(result)
            } catch (ex: Throwable) {
                OperationResult.Error(ex.message)
            }
        } else { // из памяти
            try {
                val result = cashed.getNextGif()
                OperationResult.Success(result)
            } catch (ex: Throwable) {
                OperationResult.Error(ERROR_MESSAGE)
            }
        }

    override suspend fun loadPrevGif(): OperationResult<GifDTO, String?> =
        if (cashed.isFirst) {
            // нельзя, вернуть ошибку
            OperationResult.Error("Это первая загруженная")
        } else { // загрузить
            try {
                val result = cashed.getPrevGif()
                OperationResult.Success(result)
            } catch (ex: Throwable) {
                OperationResult.Error(ERROR_MESSAGE)
            }
        }

    companion object {
        private const val ERROR_MESSAGE = "Произошла непредвиденная ошибка"
    }
}
