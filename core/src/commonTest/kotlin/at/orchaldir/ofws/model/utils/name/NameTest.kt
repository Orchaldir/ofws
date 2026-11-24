package at.orchaldir.ofws.model.utils.name

import at.orchaldir.ofws.assertIllegalArgument
import kotlin.test.Test
import kotlin.test.assertEquals

class NameTest {

    @Test
    fun `The name is trimmed`() {
        assertEquals("A", Name.init(" A").text)
        assertEquals("A", Name.init("A ").text)
        assertEquals("A", Name.init("  A  ").text)
    }

    @Test
    fun `The name is capitalized`() {
        assertEquals("A", Name.init("a").text)
        assertEquals("Test", Name.init("test ").text)
    }

    @Test
    fun `The name cannot be empty`() {
        assertIllegalArgument("Name is empty!") {
            Name.init("")
        }
    }
}