package at.orchaldir.ofws.action.element

import at.orchaldir.ofws.action.Action
import at.orchaldir.ofws.utils.Element
import at.orchaldir.ofws.utils.Id
import at.orchaldir.ofws.utils.redux.noFollowUps
import at.orchaldir.ofws.model.State
import at.orchaldir.ofws.model.app.UserId

fun reduceDeleteElement(
    state: State,
    id: Id,
): Pair<State, List<Action>> {
    return deleteElement(state, id)
}

fun <ID : Id, ELEMENT : Element<ID>, Action> deleteElement(
    state: State,
    id: ID,
): Pair<State, List<Action>> {
    val storage = state.getStorage<ID, ELEMENT>(id)
    val element = storage.getOrThrow(id)

    element.canDelete(state).validate()

    return noFollowUps(state.updateStorage(storage.remove(id)))
}
