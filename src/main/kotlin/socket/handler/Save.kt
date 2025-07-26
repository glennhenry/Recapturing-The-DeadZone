package dev.deadzone.socket.handler

import dev.deadzone.core.mission.insertLoots
import dev.deadzone.core.model.game.data.GameResources
import dev.deadzone.core.model.game.data.ZombieData
import dev.deadzone.core.model.game.data.toFlatList
import dev.deadzone.core.utils.PIOSerializer
import dev.deadzone.module.Dependency
import dev.deadzone.module.Logger
import dev.deadzone.socket.Connection
import dev.deadzone.socket.ServerContext
import dev.deadzone.socket.handler.save.compound.SaveBuildingResponse
import dev.deadzone.socket.handler.save.mission.GetZombieResponse
import dev.deadzone.socket.handler.save.mission.MissionEndResponse
import dev.deadzone.socket.handler.save.mission.MissionStartResponse
import dev.deadzone.socket.handler.save.mission.resolveAndLoadScene
import dev.deadzone.socket.utils.SocketMessage
import dev.deadzone.socket.utils.SocketMessageHandler
import io.ktor.util.date.*
import kotlin.random.Random
import kotlin.uuid.Uuid

/**
 * Handle `save` message by:
 *
 * 1. Receive the `data`, `_type`, and `id` for the said message.
 * 2. Route the save into the corresponding handler based on `_type`.
 * 3. Handlers determine what to do based on the given `data`.
 * 4. Optionally, response back a message of type 'r' with the expected JSON payload.
 *
 */
class SaveHandler(private val context: ServerContext) : SocketMessageHandler {
    override fun match(message: SocketMessage): Boolean {
        return message.contains("s") or (message.type?.equals("s") == true)
    }

    override suspend fun handle(
        connection: Connection,
        message: SocketMessage,
        send: suspend (ByteArray) -> Unit
    ) {
        val body = message.getMap("s")
        val data = body?.get("data") as? Map<String, Any?>
        val type = data?.get("_type") as? String
        val saveId = body?.get("id") as? String

        Logger.socketPrint("Save message got: type:$type | id:$saveId")

        // Note: the game typically send and expects JSON data for save message
        // encode JSON response to string before using PIO serialization

        when (type) {
            "get_offers" -> {}
            "chat_getContactsBlocks" -> {}
            "mis_start" -> {
                // IMPORTANT NOTE: the scene that involves human model is not working now (e.g., raid island human)
                // the same error is for survivor class if you fill SurvivorAppereance non-null value
                // The error was 'cylic object' thing.
                val areaType = data["areaType"] as String
                Logger.socketPrint(areaType)

                val sceneXML = resolveAndLoadScene(areaType)
                val sceneXMLWithLoot = insertLoots(sceneXML)

                val zombies = listOf(
                    ZombieData.fatWalkerStrongAttack(101),
                    ZombieData.fatWalkerStrongAttack(102),
                    ZombieData.fatWalkerStrongAttack(103),
                    ZombieData.fatWalkerStrongAttack(104),
                    ZombieData.fatWalkerStrongAttack(105),
                    ZombieData.fatWalkerStrongAttack(106),
                    ZombieData.fatWalkerStrongAttack(107),
                    ZombieData.fatWalkerStrongAttack(108),
                    ZombieData.fatWalkerStrongAttack(109),
                    ZombieData.police20ZWeakAttack(110),
                    ZombieData.riotWalker37MediumAttack(111)
                )

                val flatZombieList: List<String> = zombies.flatMap { it.toFlatList() }

                val missionStartObjectResponse = MissionStartResponse(
                    id = saveId ?: "",
                    time = 200,
                    assignmentType = "None", // for simplicity. see AssignmentType
                    areaClass = "substreet",
                    automated = false,
                    sceneXML = sceneXMLWithLoot,
                    z = flatZombieList,
                    allianceAttackerEnlisting = false,
                    allianceAttackerLockout = false,
                    allianceAttackerAllianceId = null,
                    allianceAttackerAllianceTag = null,
                    allianceMatch = false,
                    allianceRound = 0,
                    allianceRoundActive = false,
                    allianceError = false,
                    allianceAttackerWinPoints = 0
                )

                val responseJson = Dependency.json.encodeToString(missionStartObjectResponse)

                val msg = listOf(
                    "r",
                    saveId ?: "m",
                    getTimeMillis(),
                    responseJson
                )
                send(PIOSerializer.serialize(msg))
            }

            // mis_startFlag and mis_interacted do not expect a response
            "mis_startFlag" -> {
                Logger.socketPrint("===Mission start flag received===")
            }

            "mis_interacted" -> {
                Logger.socketPrint("===First interaction received===")
            }

            "mis_end" -> {
                val response = MissionEndResponse()
                val responseJson = Dependency.json.encodeToString(response)
                val resourceResponse = GameResources(
                    cash = 102000,
                    wood = (10000..100000).random(),
                    metal = (10000..100000).random(),
                    cloth = (10000..100000).random(),
                    water = (10000..100000).random(),
                    food = (10000..100000).random(),
                    ammunition = (10000..100000).random()
                )
                val resourceResponseJson = Dependency.json.encodeToString(resourceResponse)

                val msg = listOf(
                    "r",
                    saveId ?: "m",
                    getTimeMillis(),
                    responseJson,
                    resourceResponseJson
                )
                send(PIOSerializer.serialize(msg))
            }

            "mis_zombies" -> {
                val response = GetZombieResponse(
                    max = false,
                    z = emptyList()
                )
                val responseJson = Dependency.json.encodeToString(response)

                val msg = listOf(
                    "r",
                    saveId ?: "m",
                    getTimeMillis(),
                    responseJson
                )
                send(PIOSerializer.serialize(msg))
            }

            "stat_data" -> {
                val stats = data["stats"]
                Logger.socketPrint("Received stat_data with stats: $stats")
            }

            "clear_notes" -> {
                Logger.socketPrint("Received clear_notes")
            }

            "bld_move" -> {
                val x = (data["tx"] as? Number)?.toInt() ?: 0
                val y = (data["ty"] as? Number)?.toInt() ?: 0
                val r = (data["rotation"] as? Number)?.toInt() ?: 0
                val buildingId = data["id"] // use this to save in DB
                Logger.socketPrint("Building move for $saveId and $buildingId to $x,$y|r:$r")

                val response = SaveBuildingResponse(
                    success = true, x = x, y = y, r = r
                )
                val responseJson = Dependency.json.encodeToString(response)

                val msg = listOf(
                    "r",
                    saveId ?: "m",
                    getTimeMillis(),
                    responseJson
                )
                send(PIOSerializer.serialize(msg))
            }

            else -> {
                val msg = listOf(
                    "r",
                    saveId ?: "m",
                    getTimeMillis(),
                    "{}"
                )
                send(PIOSerializer.serialize(msg))
                Logger.unimplementedSocket("Handled 's' message but unhandled data type: $type from data=$data")
            }
        }
    }
}
