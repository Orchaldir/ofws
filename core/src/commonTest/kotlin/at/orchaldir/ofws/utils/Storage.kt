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
        val storage = Storage<UserId, User>(USER_TYPE)

        assertEquals(0, storage.getNextIndex(origin0))
    }

    @Test
    fun `Get the next index with an element of that origin at index 0`() {
        val storage = Storage(User(UserId(origin0, 0)))

        assertEquals(1, storage.getNextIndex(origin0))
    }

    @Test
    fun `Get the next index with an existing element of that origin at a higher index`() {
        val storage = Storage(User(UserId(origin0, 10)))

        assertEquals(0, storage.getNextIndex(origin0))
    }

    @Test
    fun `Get the next index with an element of another origin at index 0`() {
        val storage = Storage(User(UserId(origin1, 0)))

        assertEquals(0, storage.getNextIndex(origin0))
    }
}