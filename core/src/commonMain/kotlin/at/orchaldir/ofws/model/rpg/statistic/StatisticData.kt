package at.orchaldir.ofws.model.rpg.statistic

import at.orchaldir.ofws.model.utils.UnitOfMeasurement
import at.orchaldir.ofws.model.utils.Unitless
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class StatisticDataType {
    Attribute,
    DerivedAttribute,
    Skill,
}

@Serializable
sealed class StatisticData {

    fun getType() = when (this) {
        is Attribute -> StatisticDataType.Attribute
        is DerivedAttribute -> StatisticDataType.DerivedAttribute
        is Skill -> StatisticDataType.Skill
    }

    fun isBasedOn(statistic: StatisticId) = baseValue().isBasedOn(statistic)

    fun baseValue() = when (this) {
        is Attribute -> base
        is DerivedAttribute -> base
        is Skill -> base
    }

    fun cost() = when (this) {
        is Attribute -> cost
        is DerivedAttribute -> cost
        is Skill -> cost
    }

    fun display(value: Int) = when (this) {
        is DerivedAttribute -> unit.display(value)
        else -> value.toString()
    }
}

@Serializable
@SerialName("Attribute")
data class Attribute(
    val base: StatisticBasedValue = FixedNumber(0),
    val cost: StatisticCost = UndefinedStatisticCost,
) : StatisticData()

@Serializable
@SerialName("Derived")
data class DerivedAttribute(
    val base: StatisticBasedValue = FixedNumber(0),
    val cost: StatisticCost = UndefinedStatisticCost,
    val unit: UnitOfMeasurement = Unitless,
) : StatisticData()

@Serializable
@SerialName("Skill")
data class Skill(
    val base: StatisticBasedValue = FixedNumber(0),
    val cost: StatisticCost = UndefinedStatisticCost,
) : StatisticData()