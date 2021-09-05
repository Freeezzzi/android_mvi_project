package ru.freeezzzi.tinkoff_test_task.presentation.showgif

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import ru.freeezzzi.tinkoff_test_task.domain.OperationResult
import ru.freeezzzi.tinkoff_test_task.domain.usecase.LoadGifUseCase
import ru.freeezzzi.tinkoff_test_task.presentation.DataState
import ru.freeezzzi.tinkoff_test_task.presentation.baseimpl.BaseViewModel
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class ShowGifViewModel @Inject constructor(
    @Named("LoadNextUseCase") private val loadNextUseCase: LoadGifUseCase,
    @Named("LoadPrevUseCase") private val loadPrevUseCase: LoadGifUseCase,
) : BaseViewModel<ShowGifContract.Event, ShowGifContract.PictureState, ShowGifContract.Effect>() {

    private var isFirst: Boolean = true
    override fun createInitialState(): ShowGifContract.PictureState {
        return ShowGifContract.PictureState(
            DataState.empty()
        )
    }

    override fun handleEvent(event: ShowGifContract.Event) {
        when (event) {
            is ShowGifContract.Event.OnNextButtonClicked -> {
                loadGif(loadNextUseCase)
            }
            is ShowGifContract.Event.OnRetryButtonClicked -> {
                loadGif(loadNextUseCase)
            }
            is ShowGifContract.Event.OnPrevButtonClicked -> {
                if (isFirst) {
                    //setEffect { ShowGifContract.Effect.ShowToast.apply { msg = "It is already First" } }
                } else {
                    loadGif(loadPrevUseCase)
                }
            }
        }
    }

    private fun loadGif(useCase: LoadGifUseCase) {
        viewModelScope.launch {
            setState { copy(gif = DataState.loading()) }
            when (val result = useCase.load()) {
                is OperationResult.Success -> {
                    isFirst = result.data.isFirst
                    setState { copy(gif = DataState.success(result.data)) }
                }
                is OperationResult.Error -> {
                    setState { copy(gif = DataState.empty()) }
                    setEffect { ShowGifContract.Effect.ShowToast.apply { msg = "Unable to upload data" } }
                }
            }
        }
    }
}
