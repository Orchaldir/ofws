package at.orchaldir.ofws.action

import at.orchaldir.ofws.utils.Element
import at.orchaldir.ofws.utils.Id
import at.orchaldir.ofws.utils.redux.noFollowUps
import at.orchaldir.ofws.model.State
import at.orchaldir.ofws.model.app.User


fun reduceUpdateElement(
    state: State,
    element: Element<*>,
): Pair<State, List<Action>> = when (element) {
    is User -> updateElement(state, element)
    else -> error("Updating is not supported!")
}

fun <ID : Id, ELEMENT : Element<ID>, Action> updateElement(
    state: State,
    element: ELEMENT,
): Pair<State, List<Action>> {
    val storage = state.getStorage<ID, ELEMENT>(element.id())

    storage.require(element.id())
    //element.validate(state)

    return noFollowUps(state.updateStorage(storage.update(element)))
}

