package dev.deadzone.core.model.game.data

import kotlinx.serialization.Serializable

@Serializable
data class ItemAttributes(
    val baseValues: Map<String, Map<String, Double>> = mapOf(),
    val modValues: Map<String, Map<String, Double>> = mapOf(),
    val attCaps: Map<String, Map<String, Double>> = mapOf()
)
