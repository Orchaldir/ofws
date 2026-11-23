package at.orchaldir.ofws

import at.orchaldir.ofws.utils.CannotDeleteException
import at.orchaldir.ofws.utils.DeleteResult
import org.junit.Assert.assertThrows
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


inline fun <reified T : Throwable> assertFailMessage(message: String, block: () -> Unit) {
    val exception = assertFailsWith<T> { block() }
    assertEquals(message, exception.message)
}

fun assertCannotDelete(result: DeleteResult, block: () -> Unit) {
    val exception = assertThrows(CannotDeleteException::class.java) {
        block()
    }
    assertEquals(result, exception.result)
}

fun assertIllegalArgument(message: String, block: () -> Unit) =
    assertFailMessage<IllegalArgumentException>(message, block)

fun assertIllegalState(message: String, block: () -> Unit) =
    assertFailMessage<IllegalStateException>(message, block)
