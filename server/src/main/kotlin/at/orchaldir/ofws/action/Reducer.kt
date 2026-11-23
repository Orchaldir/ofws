package at.orchaldir.ofws.action

import at.orchaldir.ofws.action.element.reduceCreateElement
import at.orchaldir.ofws.action.element.reduceDeleteElement
import at.orchaldir.ofws.action.element.reduceUpdateElement
import at.orchaldir.ofws.model.State
import at.orchaldir.ofws.utils.redux.Reducer

val REDUCER: Reducer<Action, State> = { state, action ->
    when (action) {
        // meta
        is CreateAction<*> -> reduceCreateElement(state, action.id)
        is DeleteAction<*> -> reduceDeleteElement(state, action.id)
        is UpdateAction<*, *> -> reduceUpdateElement(state, action.element)
    }
}
