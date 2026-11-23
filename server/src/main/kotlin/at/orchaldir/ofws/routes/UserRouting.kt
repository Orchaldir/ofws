package at.orchaldir.ofws.routes

import at.orchaldir.ofws.action.Action
import at.orchaldir.ofws.model.State
import at.orchaldir.ofws.model.user.UserId
import at.orchaldir.ofws.utils.redux.Store
import io.ktor.server.application.*
import io.ktor.server.resources.get
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureUserRouting(store: Store<Action, State>) {
    routing {
        get<UserRoutes.Create> {
            handleNewElement(store, store.getState().getUserStorage(), ::UserId)
        }
        get<UserRoutes.GetAll> {
            val state = store.getState()

            call.respond(state.getUserStorage().getAll())
        }
        get<UserRoutes.Get> { details ->
            handleGetElement(store.getState().getUserStorage(), details.id)
        }
        get<UserRoutes.Delete> { delete ->
            handleDeleteElement(store, delete.id)
        }
        get<UserRoutes.Update> { update ->
            handleUpdateElement(store, update.user)
        }
    }
}
