package at.orchaldir.ofws.action.element

import at.orchaldir.ofws.action.REDUCER
import at.orchaldir.ofws.action.UpdateAction
import at.orchaldir.ofws.assertIllegalArgument
import at.orchaldir.ofws.model.State
import at.orchaldir.ofws.model.USER_ID_0
import at.orchaldir.ofws.model.USER_ID_UNKNOWN
import at.orchaldir.ofws.model.user.User
import at.orchaldir.ofws.model.user.UserRole
import at.orchaldir.ofws.model.utils.Name
import at.orchaldir.ofws.utils.Storage
import kotlin.test.Test
import kotlin.test.assertEquals

class UpdateTest {
    private val state = State(Storage(User(USER_ID_0)))

    @Test
    fun `Can update an existing element`() {
        val update = User(USER_ID_0, Name.init("Tester"), UserRole.Admin)
        val action = UpdateAction(update)

        assertEquals(Storage(update), REDUCER.invoke(state, action).first.getUserStorage())
    }

    @Test
    fun `Cannot update unknown id`() {
        val action = UpdateAction(User(USER_ID_UNKNOWN))

        assertIllegalArgument("Requires unknown User test_99!") { REDUCER.invoke(state, action) }
    }

}