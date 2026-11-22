package at.orchaldir.ofws.action

import at.orchaldir.ofws.utils.redux.Reducer
import at.orchaldir.ofws.model.State

val REDUCER: Reducer<Action, State> = { state, action ->
    when (action) {
        // meta
        is CreateAction<*> -> reduceCreateElement(state, action.id)
        is DeleteAction<*> -> reduceDeleteElement(state, action.id)
        is UpdateAction<*, *> -> reduceUpdateElement(state, action.element)
    }
}
