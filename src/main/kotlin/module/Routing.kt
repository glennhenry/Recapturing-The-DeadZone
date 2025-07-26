package dev.deadzone.module

import dev.deadzone.api.handler.*
import dev.deadzone.core.BigDB
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.http.content.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.io.File

fun Application.configureRouting(db: BigDB) {
    routing {
        caseInsensitiveStaticResources("/", "static")
        staticFiles("/game/core.swf", File("core-swf/core.swf"))

        post("/api/{path}") {
            val path = call.parameters["path"] ?: return@post call.respond(HttpStatusCode.BadRequest)

            when (path) {
                "13" -> authenticate(db)
                "601" -> socialRefresh(db)
                "27" -> createJoinRoom(db)
                "50" -> writeError(db)
                "85" -> loadObjects(db)
                else -> {
                    Logger.unimplementedApi("Unimplemented API route: ${path}")
                    call.respond(HttpStatusCode.NotFound, "Unimplemented API: $path")
                }
            }
        }
    }
}

/**
 * Adds a PlayerIO framing prefix to the byte array.
 *
 * This is required by the PlayerIO API message convention, which expects each request and response
 * to be prefixed with two specific bytes: `0x00` and `0x01`.
 *
 * @receiver The original unframed [ByteArray] representing a protocol buffer message.
 * @return A new [ByteArray] with `0x00` and `0x01` prepended.
 */
fun ByteArray.PIOFraming(): ByteArray {
    return byteArrayOf(0, 1) + this
}
