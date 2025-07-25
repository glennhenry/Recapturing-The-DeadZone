package dev.deadzone.core.model.game.data

import kotlinx.serialization.Serializable

@Serializable
data class SurvivorLoadoutEntry(
    val weapon: String,  // weapon id
    val gear1: String,  // gear id
    val gear2: String,  // gear id
) {
    companion object {
        fun dummy(): SurvivorLoadoutEntry {
            return SurvivorLoadoutEntry(
                weapon = "p90",
                gear1 = "",
                gear2 = "",
            )
        }
    }
}
