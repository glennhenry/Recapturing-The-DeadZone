package dev.deadzone.socket.handler.save.mission

import dev.deadzone.socket.handler.save.BaseResponse
import kotlinx.serialization.Serializable

@Serializable
data class GetZombieResponse(
    val z: List<String>,
    val max: Boolean = false, // server spawning disabled if false
): BaseResponse()
