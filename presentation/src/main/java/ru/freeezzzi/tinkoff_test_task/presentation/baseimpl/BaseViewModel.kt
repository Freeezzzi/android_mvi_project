package ru.freeezzzi.tinkoff_test_task.presentation.baseimpl

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

abstract class BaseViewModel<Event : UiEvent, State : UiState, Effect : UiEffect> : ViewModel() {

    private val initialState: State by lazy { createInitialState() }
    abstract fun createInitialState(): State

    // Get Current State
    val currentState: State
        get() = uiState.value

    private val _uiState: MutableStateFlow<State> = MutableStateFlow(initialState)
    val uiState = _uiState.asStateFlow()

    private val _event: MutableSharedFlow<Event> = MutableSharedFlow()
    val event = _event.asSharedFlow()

    private val _effect: Channel<Effect> = Channel()
    val effect = _effect.receiveAsFlow()

    fun setEvent(event: Event) {
        val newEvent = event
        viewModelScope.launch { _event.emit(newEvent) }
    }

    protected fun setState(reduce: State.() -> State) {
        val newState = currentState.reduce()
        _uiState.value = newState
    }

    protected fun setEffect(builder: () -> Effect) {
        val effectValue = builder()
        viewModelScope.launch { _effect.send(effectValue) }
    }

    init {
        subscribeEvents()
    }

    private fun subscribeEvents() {
        viewModelScope.launch {
            event.collect {
                handleEvent(it)
            }
        }
    }

    /**
     * Handle each event
     */
    abstract fun handleEvent(event: Event)
}

/**
 * Current state of views
 */
interface UiState

/**
 * User actions
 */
interface UiEvent

/**
 * Things that we want show only once(SingleLiveEvent)
 */
interface UiEffect

/**
 * Shared flow - для множества подписчиков. Для ивентов, которые мы хотим выполнить немедленно или не выполнять вообще. Ивент пропадает если нет подписчиков
 * State flow - то же что и Livedata, но с начальным значением, поэтмоу всегда есть значение.
 * Channel - Доставляет сообщение 1 получателю, сообщения не пропадают по дефолту.
 */
