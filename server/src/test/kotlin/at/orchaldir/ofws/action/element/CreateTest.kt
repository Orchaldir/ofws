package at.orchaldir.ofws.action.element

import at.orchaldir.ofws.action.CreateAction
import at.orchaldir.ofws.action.REDUCER
import at.orchaldir.ofws.model.State
import at.orchaldir.ofws.model.USER_ID_0
import at.orchaldir.ofws.model.user.User
import at.orchaldir.ofws.utils.Storage
import kotlin.test.*

class CreateTest {

    @Test
    fun `Create an element with the correct id`() {
        val actual = REDUCER.invoke(State(), CreateAction(USER_ID_0)).first.getUserStorage()

        assertEquals(Storage(User(USER_ID_0)), actual)
    }

}