package at.orchaldir.ofws.routes

import at.orchaldir.ofws.action.Action
import at.orchaldir.ofws.action.CreateAction
import at.orchaldir.ofws.action.DeleteAction
import at.orchaldir.ofws.action.UpdateAction
import at.orchaldir.ofws.model.State
import at.orchaldir.ofws.utils.CannotDeleteException
import at.orchaldir.ofws.utils.Element
import at.orchaldir.ofws.utils.Id
import at.orchaldir.ofws.utils.Storage
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

suspend inline fun  <ID : Id, reified ELEMENT : Element<ID>> RoutingContext.handleGetElement(
    storage: Storage<ID, ELEMENT>,
    id: ID,
) {
    storage.get(id)?.let {
        call.respond(it)
        return
    }

    call.respond(HttpStatusCode.NotFound)
}

suspend inline fun <reified ID : Id, reified ELEMENT : Element<ID>> RoutingContext.handleNewElement(
    store: Store<Action, State>,
    storage: Storage<ID, ELEMENT>,
    create: (String, Int) -> ID,
) {
    val origin = store.getState().modName
    val index = storage.getNextIndex(origin)
    val id = create(origin, index)

    logger.info { "Create ${id.print()}" }

    try {
        store.dispatch(CreateAction(id))
        call.respond(id)
    } catch (e: IllegalArgumentException) {
        logger.warn { e.message }
        call.respond(HttpStatusCode.BadRequest)
    }
}

suspend inline fun <ID : Id, reified ELEMENT : Element<ID>> RoutingContext.handleUpdateElement(
    store: Store<Action, State>,
    element: ELEMENT,
) {
    logger.info { "Update ${element.id().print()}" }

    try {
        store.dispatch(UpdateAction(element))
        call.respond(HttpStatusCode.NoContent)
    } catch (e: IllegalArgumentException) {
        logger.warn { e.message }
        call.respond(HttpStatusCode.BadRequest)
    }
}
