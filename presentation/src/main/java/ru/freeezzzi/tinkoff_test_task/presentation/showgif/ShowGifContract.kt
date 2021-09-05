package ru.freeezzzi.tinkoff_test_task.presentation.showgif

import ru.freeezzzi.tinkoff_test_task.domain.model.GifDTO
import ru.freeezzzi.tinkoff_test_task.presentation.DataState
import ru.freeezzzi.tinkoff_test_task.presentation.baseimpl.UiEffect
import ru.freeezzzi.tinkoff_test_task.presentation.baseimpl.UiEvent
import ru.freeezzzi.tinkoff_test_task.presentation.baseimpl.UiState

class ShowGifContract {
    sealed class Event : UiEvent {
        object OnNextButtonClicked : Event()
        object OnPrevButtonClicked : Event()
        object OnRetryButtonClicked : Event()
    }

    // Ui View States
    data class PictureState(
        val gif: DataState<GifDTO>
    ) : UiState

    // Side effects
    sealed class Effect : UiEffect {

        object ShowToast: Effect(){
            var msg:String? = null
        }
    }
}
