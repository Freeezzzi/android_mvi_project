package ru.freeezzzi.tinkoff_test_task.domain.model

data class GifDTO(
    val title: String,
    val url: String,
    val isFirst: Boolean // сколько gif хранится в памяти до данной. Если 0, то это первая загруженная.
)
