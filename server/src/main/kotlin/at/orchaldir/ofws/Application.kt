package at.orchaldir.ofws

import at.orchaldir.ofws.action.REDUCER
import at.orchaldir.ofws.model.State
import at.orchaldir.ofws.model.user.User
import at.orchaldir.ofws.model.user.UserId
import at.orchaldir.ofws.model.user.UserRole
import at.orchaldir.ofws.model.utils.name.Name
import at.orchaldir.ofws.routes.configureUserRouting
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
import io.ktor.server.resources.*

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install()

    val admin = User(UserId("app", 0), Name.init("Admin"), UserRole.Admin)
    val state = State(Storage(admin), "app")
    val store = DefaultStore(state, REDUCER, listOf(LogAction()))

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