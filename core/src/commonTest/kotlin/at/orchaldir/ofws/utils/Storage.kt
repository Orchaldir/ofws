package at.orchaldir.ofws.utils

import at.orchaldir.ofws.model.user.USER_TYPE
import at.orchaldir.ofws.model.user.User
import at.orchaldir.ofws.model.user.UserId
import kotlin.test.*


class StoreTest {

    private val origin0 = "a"
    private val origin1 = "b"

    @Test
    fun `Get the next index of an empty storage`() {
        assertNextIndex(emptyList(), 0)
    }

    @Test
    fun `Get the next index with an element of that origin at index 0`() {
        assertNextIndex(UserId(origin0, 0), 1)
    }

    @Test
    fun `Get the next index with multiple elements of that origin at index 0`() {
        val ids = listOf(
            UserId(origin0, 0),
            UserId(origin0, 1),
            UserId(origin0, 3),
        )

        assertNextIndex(ids, 2)
    }

    @Test
    fun `Get the next index with an existing element of that origin at a higher index`() {
        assertNextIndex(UserId(origin0, 10), 0)
    }

    @Test
    fun `Get the next index with an element of another origin at index 0`() {
        assertNextIndex(UserId(origin1, 0), 0)
    }

    private fun assertNextIndex(id: UserId, expected: Int) =
        assertNextIndex(listOf(id), expected)

    private fun assertNextIndex(ids: List<UserId>, expected: Int) {
        val storage = Storage(ids.map { User(it) }, USER_TYPE)

        assertEquals(expected, storage.getNextIndex(origin0))
    }
}