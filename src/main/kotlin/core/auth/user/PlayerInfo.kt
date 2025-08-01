package core.auth.user

/**
 * Server-level representation of player credentials (not to be confused with game-level representation PlayerData)
 */
data class PlayerInfo(
    val playerId: String,
    val nickname: String,
    val isAdmin: Boolean = false,
    val extra: Map<String, Any?> = emptyMap()
)
