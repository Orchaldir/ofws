package at.orchaldir.ofws.action.element

import at.orchaldir.ofws.action.DeleteAction
import at.orchaldir.ofws.action.REDUCER
import at.orchaldir.ofws.assertIllegalArgument
import at.orchaldir.ofws.model.State
import at.orchaldir.ofws.model.USER_ID_0
import at.orchaldir.ofws.model.USER_ID_UNKNOWN
import at.orchaldir.ofws.model.user.User
import at.orchaldir.ofws.utils.Storage
import kotlin.test.Test
import kotlin.test.assertEquals

class DeleteTest {
    private val action = DeleteAction(USER_ID_0)
    private val state = State(Storage(User(USER_ID_0)))

    @Test
    fun `Can delete an existing element`() {
        assertEquals(0, REDUCER.invoke(state, action).first.getUserStorage().getSize())
    }

    @Test
    fun `Cannot delete unknown id`() {
        val action = DeleteAction(USER_ID_UNKNOWN)

        assertIllegalArgument("Requires unknown User test_99!") { REDUCER.invoke(state, action) }
    }

}