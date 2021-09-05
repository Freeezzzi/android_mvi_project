package ru.freeezzzi.tinkoff_test_task.data.network.models

import com.squareup.moshi.JsonClass
import ru.freeezzzi.tinkoff_test_task.domain.model.GifDTO
import java.io.Serializable

@JsonClass(generateAdapter = true)
data class RandomDTO(
    val id: Long,
    val description: String,
    val votes: Int,
    val author: String,
    val date: String,
    val gifURL: String,
    val gifSize: Int,
    val previewURL: String,
    val videoURL: String,
    val videoPath: String,
    val videoSize: Int,
    val type: String,
    val width: String,
    val height: String,
    val commentsCount: Int,
    val fileSize: Int,
    val canVote: Boolean
) : Serializable

fun RandomDTO.toGifDTO(isFirst: Boolean): GifDTO =
    GifDTO(
        title = description,
        url = gifURL,
        isFirst = isFirst
    )
