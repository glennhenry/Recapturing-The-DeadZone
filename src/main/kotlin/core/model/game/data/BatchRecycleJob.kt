package dev.deadzone.core.model.game.data

import kotlinx.serialization.Serializable
import dev.deadzone.core.model.game.data.Item

@Serializable
data class BatchRecycleJob(
    val id: String,
    val items: List<Item>,
    val start: Long,
    val end: Int
)
