package dev.deadzone.core.model.game.data

import kotlinx.serialization.Serializable

@Serializable
data class ZombieData(
    val id: Int,
    val type: String,
    val weapon: String
) {
    companion object {
        fun fatWalkerStrongAttack(id: Int): ZombieData {
            return ZombieData(id = id, type = "fat-walker", weapon = "zStrongStrike")
        }

        fun police20ZWeakAttack(id: Int): ZombieData {
            return ZombieData(id = id, type = "police-20", weapon = "zStrike")
        }

        fun riotWalker37MediumAttack(id: Int): ZombieData {
            return ZombieData(id = id, type = "riot-walker-37", weapon = "zMediumStrike")
        }
    }
}

fun ZombieData.toFlatList(): List<String> {
    return listOf("$id", type, weapon)
}
