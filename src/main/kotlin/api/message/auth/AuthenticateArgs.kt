package dev.deadzone.api.message.auth

import kotlinx.serialization.Serializable

@Serializable
data class AuthenticateArgs(
    val gameId: String = "",
    val userId: String = ""
)
