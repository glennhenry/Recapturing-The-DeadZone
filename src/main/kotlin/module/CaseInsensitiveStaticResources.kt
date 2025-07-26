package dev.deadzone.module

import io.github.classgraph.ClassGraph
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.defaultForFileExtension
import io.ktor.server.application.Application
import io.ktor.server.request.path
import io.ktor.server.response.respond
import io.ktor.server.response.respondBytes
import io.ktor.server.routing.get
import io.ktor.server.routing.routing

fun Application.caseInsensitiveStaticResources(baseUrl: String, resourceRoot: String = "static") {
    val resourceMap = scanClasspathResources(resourceRoot)

    for (key in resourceMap.keys) {
        println("$key -> ${resourceMap[key]}")
    }

    routing {
        get("$baseUrl/{...}") {
            val rawPath = call.request.path().replace(Regex("/+"), "/")
            val lookupKey = if (rawPath.startsWith("/game/data")) {
                "static" + rawPath.lowercase()
            } else {
                "static$rawPath"
            }


            val actualResourcePath = resourceMap[lookupKey]

            println("🔶 Serving $rawPath")
            println("lookupKey: $lookupKey")
            println("actual: $actualResourcePath")

            if (actualResourcePath != null) {
                val resource = Application::class.java.getResourceAsStream("/$actualResourcePath")
                if (resource != null) {
                    val contentType = ContentType.defaultForFileExtension(
                        rawPath.substringAfterLast('.', "")
                    )
                    return@get call.respondBytes(resource.readBytes(), contentType)
                }
            }

            call.respond(HttpStatusCode.NotFound)
        }

        // Special case for serving / (root) as index.html (case-sensitive)
        get(baseUrl) {
            println("🔶 Serving index.html")
            val indexPath = "$resourceRoot/index.html"
            val stream =
                resourceMap[indexPath]?.let { this::class.java.classLoader.getResourceAsStream(it) }

            if (stream != null) {
                return@get call.respondBytes(stream.readBytes(), ContentType.Text.Html)
            }

            call.respond(HttpStatusCode.NotFound)
        }
    }
}

fun scanClasspathResources(basePackage: String): Map<String, String> {
    val map = mutableMapOf<String, String>()

    ClassGraph().acceptPaths(basePackage).scan().use { scanResult ->
        for (res in scanResult.allResources) {
            val fullPath = res.path                      // e.g. static/game/data/models/...
            map[fullPath] = fullPath                     // exact‑case key  ( still useful )
            if (fullPath.startsWith("$basePackage/game/data/", ignoreCase = true)) {
                map[fullPath.lowercase()] = fullPath     // case‑insensitive alias
            }
        }
    }
    return map
}
