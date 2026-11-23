package at.orchaldir.ofws.action

import at.orchaldir.ofws.utils.Element
import at.orchaldir.ofws.utils.Id

sealed class Action

//-- generic --

// element
data class CreateAction<ID : Id>(val id: ID) : Action()
data class DeleteAction<ID : Id>(val id: ID) : Action()
data class UpdateAction<ID : Id, ELEMENT : Element<ID>>(val element: ELEMENT) : Action()
