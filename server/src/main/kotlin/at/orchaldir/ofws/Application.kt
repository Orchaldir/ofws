package at.orchaldir.ofws

import at.orchaldir.ofws.action.REDUCER
import at.orchaldir.ofws.model.State
import at.orchaldir.ofws.model.app.User
import at.orchaldir.ofws.model.app.UserId
import at.orchaldir.ofws.model.app.UserRole
import at.orchaldir.ofws.routes.app.configureUserRouting
import at.orchaldir.ofws.utils.Name
import at.orchaldir.ofws.utils.Storage
import at.orchaldir.ofws.utils.redux.DefaultStore
import at.orchaldir.ofws.utils.redux.middleware.LogAction
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.resources.Resources
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install()

    val admin = User(UserId("app", 0), Name.init("Admin"), UserRole.Admin)
    val users = mutableListOf(admin)
    (0..10).forEach {
        users.add(User(UserId("test", it)))
    }
    val state = State(Storage(users))
    val store = DefaultStore(state, REDUCER, listOf(LogAction()))

    routing {
        get("/") {
            call.respondText(sayHello("Ktor"))
        }
    }
    configureUserRouting(store)
}

private fun Application.install() {
    install(ContentNegotiation) {
        json()
    }
    install(CORS) {
        allowHeader(HttpHeaders.ContentType)
        allowMethod(HttpMethod.Delete)
        anyHost()
    }
    install(Resources)
}