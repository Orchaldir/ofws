package at.orchaldir.ofws.routes.app

import at.orchaldir.ofws.action.Action
import at.orchaldir.ofws.model.State
import at.orchaldir.ofws.utils.redux.Store
import io.ktor.server.application.*
import io.ktor.server.resources.get
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureUserRouting(store: Store<Action, State>) {
    routing {
        get<UserRoutes.All> {
            val state = store.getState()

            call.respond(state.getUserStorage().getAll())
        }
    }
}
