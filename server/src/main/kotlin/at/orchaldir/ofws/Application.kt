package at.orchaldir.ofws

import at.orchaldir.ofws.action.REDUCER
import at.orchaldir.ofws.model.State
import at.orchaldir.ofws.utils.redux.DefaultStore
import at.orchaldir.ofws.utils.redux.middleware.LogAction
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.http.*
import io.ktor.serialization.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install(ContentNegotiation) {
        json()
    }
    install(CORS) {
        allowHeader(HttpHeaders.ContentType)
        allowMethod(HttpMethod.Delete)
        anyHost()
    }
    val store = DefaultStore(State(), REDUCER, listOf(LogAction()))

    routing {
        get("/") {
            call.respondText(sayHello("Ktor"))
        }
    }
}