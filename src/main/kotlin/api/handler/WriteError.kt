package dev.deadzone.api.handler

import dev.deadzone.api.message.utils.WriteErrorArgs
import dev.deadzone.api.message.utils.WriteErrorError
import dev.deadzone.core.data.BigDB
import dev.deadzone.module.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.utils.io.*
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.decodeFromByteArray
import kotlinx.serialization.encodeToByteArray
import kotlinx.serialization.protobuf.ProtoBuf

/**
 * WriteError (API 50)
 *
 * Input: `WriteErrorArgs`
 *
 * Output: `WriteErrorError` (optional)
 */
@OptIn(ExperimentalSerializationApi::class)
suspend fun RoutingContext.writeError(db: BigDB) {
    val writeErrorArgs = ProtoBuf.decodeFromByteArray<WriteErrorArgs>(
        call.receiveChannel().toByteArray()
    )

    logInput("\n" + writeErrorArgs)

    Logger.error(LogConfigWriteError) { writeErrorArgs.toString() }

    if (writeErrorArgs.details.contains("Load Never Completed", ignoreCase = true) ||
        writeErrorArgs.details.contains("Resource not found", ignoreCase = true)
    ) {
        val regex = Regex("""URL:\s*(?:https?://[^/]+)?(//game/[^ \n]+)""")
        val match = regex.find(writeErrorArgs.details)

        if (match != null) {
            val assetPath = match.groupValues[2]
            Logger.error(LogConfigAssetsError) { "MISSING ASSETS [please report]: $assetPath" }
        } else {
            Logger.error(LogConfigAssetsError) { writeErrorArgs.details }
        }
    }

    val writeErrorError = ProtoBuf.encodeToByteArray<WriteErrorError>(
        WriteErrorError.dummy()
    )

    logOutput(writeErrorError)

    call.respondBytes(writeErrorError.pioFraming())
}
