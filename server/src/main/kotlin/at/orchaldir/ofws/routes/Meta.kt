package at.orchaldir.ofws.routes

import at.orchaldir.ofws.action.Action
import at.orchaldir.ofws.action.DeleteAction
import at.orchaldir.ofws.model.State
import at.orchaldir.ofws.utils.CannotDeleteException
import at.orchaldir.ofws.utils.Id
import at.orchaldir.ofws.utils.redux.Store
import io.ktor.http.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import mu.KotlinLogging

val logger = KotlinLogging.logger {}

suspend inline fun RoutingContext.handleDeleteElement(
    store: Store<Action, State>,
    id: Id,
) {
    logger.info { "Delete ${id.print()}" }

    try {
        store.dispatch(DeleteAction(id))
        call.respond(HttpStatusCode.NoContent)
    } catch (e: CannotDeleteException) {
        logger.warn { e.message }
        call.respond(HttpStatusCode.BadRequest)
    }
}
