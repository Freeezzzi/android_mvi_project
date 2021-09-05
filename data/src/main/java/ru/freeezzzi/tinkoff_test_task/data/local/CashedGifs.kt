package ru.freeezzzi.tinkoff_test_task.data.local

import ru.freeezzzi.tinkoff_test_task.domain.model.GifDTO

object CashedGifs {
    private var currentPos = -1
    private val gifsList = mutableListOf<GifDTO>()

    val isLast: Boolean get() = currentPos == gifsList.size - 1
    val isFirstlyAdded: Boolean get() = gifsList.size == 0
    val isFirst: Boolean get() = currentPos <= 0

    fun saveNewGif(gifDTO: GifDTO) {
        gifsList.add(gifDTO)
        currentPos++
    }

    fun getPrevGif(): GifDTO = gifsList[--currentPos]

    fun getNextGif(): GifDTO = gifsList[++currentPos]
}
