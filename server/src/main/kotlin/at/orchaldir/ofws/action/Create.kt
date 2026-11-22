package at.orchaldir.ofws.action

import at.orchaldir.ofws.utils.Element
import at.orchaldir.ofws.utils.Id
import at.orchaldir.ofws.utils.redux.noFollowUps
import at.orchaldir.ofws.model.State
import at.orchaldir.ofws.model.app.User
import at.orchaldir.ofws.model.app.UserId

fun reduceCreateElement(
    state: State,
    id: Id,
): Pair<State, List<Action>> = when (id) {
    is UserId -> createElement(state, User(id))
    else -> error("Creating is not supported!")
}

fun <ID : Id, ELEMENT : Element<ID>, Action> createElement(
    state: State,
    element: ELEMENT,
): Pair<State, List<Action>> {
    val storage = state.getStorage<ID, ELEMENT>(element.id())

    return noFollowUps(state.updateStorage(storage.add(element)))
}
