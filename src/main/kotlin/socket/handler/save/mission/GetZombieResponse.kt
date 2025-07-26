package dev.deadzone.socket.handler.save.mission

import dev.deadzone.core.model.game.data.ZombieData
import dev.deadzone.socket.handler.save.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class GetZombieResponse(
    val z: List<ZombieData> = listOf(
        ZombieData.fatWalker(10),
        ZombieData.fatWalker(12),
        ZombieData.fatWalker(12),
        ZombieData.fatWalker(16),
        ZombieData.fatWalker(15),
    ),
    val max: Boolean = true, // server spawning disabled if true
): BaseResponse()
