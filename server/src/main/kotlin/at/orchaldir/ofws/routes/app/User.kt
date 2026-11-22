package at.orchaldir.ofws.routes.app

import at.orchaldir.ofws.action.Action
import at.orchaldir.ofws.model.State
import at.orchaldir.ofws.model.app.USER_TYPE
import at.orchaldir.ofws.utils.redux.Store
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


fun Application.configureUserRouting(store: Store<Action, State>) {
    routing {
        route("/$USER_TYPE") {
            get {
                val state = store.getState()

                call.respond(state.getUserStorage().getAll())
            }
        }
    }
}
