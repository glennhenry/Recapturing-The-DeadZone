package dev.deadzone.core.model.game.data

import kotlinx.serialization.Serializable

@Serializable
data class ZombieData(
    val id: String,
    val type: String,
    val weapon: String = ""
) {
    companion object {
        fun fatWalker(weapon: String = ""): ZombieData {
            return ZombieData(id = "fat-walker", type = "fat-walker", weapon = weapon)
        }
    }
}
