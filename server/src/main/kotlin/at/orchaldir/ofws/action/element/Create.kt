package at.orchaldir.ofws.action.element

import at.orchaldir.ofws.action.Action
import at.orchaldir.ofws.model.State
import at.orchaldir.ofws.model.rpg.statistic.Statistic
import at.orchaldir.ofws.model.rpg.statistic.StatisticId
import at.orchaldir.ofws.model.user.User
import at.orchaldir.ofws.model.user.UserId
import at.orchaldir.ofws.utils.Element
import at.orchaldir.ofws.utils.Id
import at.orchaldir.ofws.utils.redux.noFollowUps

fun reduceCreateElement(
    state: State,
    id: Id,
): Pair<State, List<Action>> {
    val element = when (id) {
        is UserId -> User(id)
        is StatisticId -> Statistic(id)
        else -> error("Creating is not supported!")
    }

    return createElement(state, element)
}

private fun <ID : Id, ELEMENT : Element<ID>, Action> createElement(
    state: State,
    element: ELEMENT,
): Pair<State, List<Action>> {
    val storage = state.getStorage<ID, ELEMENT>(element.id())

    return noFollowUps(state.updateStorage(storage.add(element)))
}
