package at.orchaldir.ofws.model.rpg.statistic

import kotlin.test.Test
import kotlin.test.assertEquals

class StatisticCostTest {

    @Test
    fun `The cost of an undefined cost is always 0`() {
        val cost = UndefinedStatisticCost

        assertCost(cost, -1, 0)
        assertCost(cost, 0, 0)
        assertCost(cost, 1, 0)
    }

    @Test
    fun `The cost of an fixed cost`() {
        val cost = FixedStatisticCost(20)

        assertCost(cost, -1, -20)
        assertCost(cost, 0, 0)
        assertCost(cost, 1, 20)
    }

    @Test
    fun `The cost of a GURPS skill`() {
        val cost = GurpsSkillCost

        assertCost(cost, -1, 0)
        assertCost(cost, 0, 0)
        assertCost(cost, 1, 1)
        assertCost(cost, 2, 2)
        assertCost(cost, 3, 4)
        assertCost(cost, 4, 8)
        assertCost(cost, 5, 12)
    }

    private fun assertCost(cost: StatisticCost, level: Int, result: Int) {
        assertEquals(result, cost.calculate(level))
    }

}