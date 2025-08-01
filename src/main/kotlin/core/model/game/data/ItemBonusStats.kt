package dev.deadzone.core.model.game.data

import kotlinx.serialization.Serializable

@Serializable
data class ItemBonusStats(
    val stat_srv: Map<String, Double>? = null,
    val stat_weap: Map<String, Double>? = null,
    val stat_gear: Map<String, Double>? = null
)
