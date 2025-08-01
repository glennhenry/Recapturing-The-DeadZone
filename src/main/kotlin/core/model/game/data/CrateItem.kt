package dev.deadzone.core.model.game.data

import kotlinx.serialization.Serializable
import dev.deadzone.socket.handler.saveresponse.crate.gachaPoolExample

@Serializable
data class CrateItem(
    val type: String,
    val id: String = "",  // uses GUID.create() by default
    val new: Boolean = false,
    val storeId: String = "",
    val level: Int = 0,
    val series: Int = 0,
    val version: Int = 0,
    val contents: List<Item> = gachaPoolExample
)
