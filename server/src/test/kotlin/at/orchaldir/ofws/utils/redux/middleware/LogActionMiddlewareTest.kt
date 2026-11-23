package at.orchaldir.ofws.utils.redux.middleware

import at.orchaldir.ofws.utils.redux.DefaultStore
import at.orchaldir.ofws.utils.redux.noFollowUps
import kotlin.test.Test
import kotlin.test.assertEquals

class LogActionMiddlewareTest {

    @Test
    fun `Logging of actions`() {
        val store = DefaultStore<Int, Int>(
            10,
            {
                    state,
                    action,
                ->
                noFollowUps(state + action)
            },
            listOf(LogAction())
        )

        store.dispatch(3)

        assertEquals(13, store.getState())
    }

}