package at.orchaldir.ofws.action

import at.orchaldir.ofws.utils.Element
import at.orchaldir.ofws.utils.Id
import at.orchaldir.ofws.utils.redux.noFollowUps
import at.orchaldir.ofws.model.State
import at.orchaldir.ofws.model.app.UserId

fun reduceDeleteElement(
    state: State,
    id: Id,
): Pair<State, List<Action>> = when (id) {
    is UserId -> deleteElement(state, id, State::canDeleteAmmunition)

    else -> error("Deleting is not supported!")
}

fun <ID : Id, ELEMENT : Element<ID>, Action> deleteElement(
    state: State,
    id: ID,
    validation: (State, ID) -> DeleteResult,
): Pair<State, List<Action>> {
    val storage = state.getStorage<ID, ELEMENT>(id)

    storage.require(id)
    //validation.invoke(state, id).validate()

    return noFollowUps(state.updateStorage(storage.remove(id)))
}
