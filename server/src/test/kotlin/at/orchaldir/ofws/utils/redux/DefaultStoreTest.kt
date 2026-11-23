package at.orchaldir.ofws.utils.redux

import kotlin.test.*

class StoreTest {

    @Test
    fun `Get initial state`() {
        val store = createStore()

        assertEquals(10, store.getState())
    }

    @Test
    fun `Dispatch action`() {
        val store = createStore()

        store.dispatch(3)

        assertEquals(13, store.getState())
    }

    @Test
    fun `Dispatch action with follow ups`() {
        val store = DefaultStore<Int, Int>(10, { state, action ->
            Pair(state + action, createFollowUp(action))
        })

        store.dispatch(3)

        assertEquals(16, store.getState())
    }

    @Test
    fun `Subscriber gets update after dispatch`() {
        val store = createStore()
        var calls = 0
        val stateList = mutableListOf<Int>()

        store.subscribe { new ->
            calls++
            stateList.add(new)
        }

        store.dispatch(5)

        assertEquals(1, calls)
        assertEquals(listOf(15), stateList)

        store.dispatch(-1)

        assertEquals(2, calls)
        assertEquals(listOf(15, 14), stateList)
    }

    private fun createStore() = DefaultStore<Int, Int>(10, { state, action -> noFollowUps(state + action) })

    private fun createFollowUp(action: Int) = if (action > 1) listOf(action - 1) else emptyList()
}
