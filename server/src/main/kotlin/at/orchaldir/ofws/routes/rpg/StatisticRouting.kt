package at.orchaldir.ofws.routes.rpg

import at.orchaldir.ofws.action.Action
import at.orchaldir.ofws.model.State
import at.orchaldir.ofws.model.rpg.statistic.StatisticId
import at.orchaldir.ofws.routes.handleDeleteElement
import at.orchaldir.ofws.routes.handleGetElement
import at.orchaldir.ofws.routes.handleNewElement
import at.orchaldir.ofws.routes.handleUpdateElement
import at.orchaldir.ofws.utils.redux.Store
import io.ktor.server.application.*
import io.ktor.server.resources.get
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureStatisticRouting(store: Store<Action, State>) {
    routing {
        get<StatisticRoutes.Create> {
            handleNewElement(store, store.getState().getStatisticStorage(), ::StatisticId)
        }
        get<StatisticRoutes.GetAll> {
            val state = store.getState()

            call.respond(state.getStatisticStorage().getAll())
        }
        get<StatisticRoutes.Get> { details ->
            handleGetElement(store.getState().getStatisticStorage(), details.id)
        }
        get<StatisticRoutes.Delete> { delete ->
            handleDeleteElement(store, delete.id)
        }
        get<StatisticRoutes.Update> { update ->
            handleUpdateElement(store, update.statistic)
        }
    }
}
