package at.orchaldir.ofws.model.rpg.statistic

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class StatisticCostType {
    Undefined,
    Fixed,
    GurpsSkill,
}

@Serializable
sealed class StatisticCost {

    fun getType() = when (this) {
        is FixedStatisticCost -> StatisticCostType.Fixed
        GurpsSkillCost -> StatisticCostType.GurpsSkill
        UndefinedStatisticCost -> StatisticCostType.Undefined
    }

    fun calculate(relativeLevel: Int) = when (this) {
        is FixedStatisticCost -> relativeLevel * cost
        GurpsSkillCost -> if (relativeLevel <= 0) {
            0
        } else if (relativeLevel == 1) {
            1
        } else if (relativeLevel <= 2) {
            2
        } else {
            (relativeLevel - 2) * 4
        }

        UndefinedStatisticCost -> 0
    }
}

@Serializable
@SerialName("Fixed")
data class FixedStatisticCost(
    val cost: Int,
) : StatisticCost()

@Serializable
@SerialName("GurpsSkill")
data object GurpsSkillCost : StatisticCost()

@Serializable
@SerialName("Undefined")
data object UndefinedStatisticCost : StatisticCost()