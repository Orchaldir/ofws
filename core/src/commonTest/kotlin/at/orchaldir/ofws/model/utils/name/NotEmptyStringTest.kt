package at.orchaldir.ofws.model.utils.name

import at.orchaldir.ofws.assertIllegalArgument
import kotlin.test.Test
import kotlin.test.assertEquals

class NotEmptyStringTest {

    @Test
    fun `The string is trimmed`() {
        assertEquals("A", NotEmptyString.init(" A").text)
        assertEquals("A", NotEmptyString.init("A ").text)
        assertEquals("A", NotEmptyString.init("  A  ").text)
    }

    @Test
    fun `The string is not capitalized`() {
        assertEquals("a", NotEmptyString.init("a").text)
        assertEquals("test", NotEmptyString.init("test ").text)
    }

    @Test
    fun `The string cannot be empty`() {
        assertIllegalArgument("NotEmptyString is empty!") {
            NotEmptyString.init("")
        }
    }
}