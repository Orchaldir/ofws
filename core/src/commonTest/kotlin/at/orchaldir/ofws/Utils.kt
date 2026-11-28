package at.orchaldir.ofws

import kotlin.test.assertEquals
import kotlin.test.assertFailsWith


inline fun <reified T : Throwable> assertFailMessage(message: String, block: () -> Unit) {
    val exception = assertFailsWith<T> { block() }
    assertEquals(message, exception.message)
}

fun assertIllegalArgument(message: String, block: () -> Unit) =
    assertFailMessage<IllegalArgumentException>(message, block)

fun assertIllegalState(message: String, block: () -> Unit) =
    assertFailMessage<IllegalStateException>(message, block)
