package at.orchaldir.ofws.model.utils

import at.orchaldir.ofws.model.utils.name.NotEmptyString
import kotlin.test.Test
import kotlin.test.assertEquals

class UnitOfMeasurementTest {

    private val text = NotEmptyString.init("A")

    @Test
    fun `Test unitless`() {
        assertEquals("42", Unitless.display(42))
    }

    @Test
    fun `Test prefix`() {
        assertEquals("A 42", PrefixedUnit(text).display(42))
    }

    @Test
    fun `Test suffix`() {
        assertEquals("42 A", SuffixedUnit(text).display(42))
    }
}