package at.orchaldir.ofws.utils

import kotlin.test.Test
import kotlin.test.assertEquals


class SplitNameTest {

    private enum class TestEnum {
        Test,
        TwoWords,
        TheVeryLongExample,
        ABC,
    }

    @Test
    fun `Do not split a single word`() {
        assertEquals(listOf("Test"), TestEnum.Test.splitName())
    }

    @Test
    fun `Split 2 words`() {
        assertEquals(listOf("Two", "Words"), TestEnum.TwoWords.splitName())
    }

    @Test
    fun `Split multiple words`() {
        assertEquals(
            listOf("The", "Very", "Long", "Example"),
            TestEnum.TheVeryLongExample.splitName(),
        )
    }

    @Test
    fun `Do not split an acronym`() {
        assertEquals(listOf("ABC"), TestEnum.ABC.splitName())
    }

    @Test
    fun `Get name with spaces`() {
        assertEquals("The Very Long Example", TestEnum.TheVeryLongExample.nameWithSpaces())
    }
}