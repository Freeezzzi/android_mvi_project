package ru.freeezzzi.tinkoff_test_task.data.network

import retrofit2.http.GET
import ru.freeezzzi.tinkoff_test_task.data.network.models.RandomDTO

interface API {

    @GET("random?json=true")
    suspend fun getRandomGif(): RandomDTO
}
