package at.orchaldir.ofws.model.rpg.statistic

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class StatisticBasedValueType {
    Division,
    FixedNumber,
    Lookup,
    Product,
    Sum,
}

@Serializable
sealed class StatisticBasedValue {

    fun getType() = when (this) {
        is StatisticLookup -> StatisticBasedValueType.Lookup
        is DivisionOfValues -> StatisticBasedValueType.Division
        is FixedNumber -> StatisticBasedValueType.FixedNumber
        is ProductOfValues -> StatisticBasedValueType.Product
        is SumOfValues -> StatisticBasedValueType.Sum
    }

    fun isBasedOn(other: StatisticId): Boolean = when (this) {
        is StatisticLookup -> statistic == other
        is DivisionOfValues -> dividend.isBasedOn(other) || divisor.isBasedOn(other)
        is FixedNumber -> false
        is ProductOfValues -> values.any { it.isBasedOn(other) }
        is SumOfValues -> values.any { it.isBasedOn(other) }
    }
}

@Serializable
@SerialName("Fixed")
data class FixedNumber(
    val default: Int,
) : StatisticBasedValue()

@Serializable
@SerialName("Lookup")
data class StatisticLookup(
    val statistic: StatisticId,
) : StatisticBasedValue()

@Serializable
@SerialName("Sum")
data class SumOfValues(
    val values: List<StatisticBasedValue>,
) : StatisticBasedValue()

@Serializable
@SerialName("Product")
data class ProductOfValues(
    val values: List<StatisticBasedValue>,
) : StatisticBasedValue()

@Serializable
@SerialName("Division")
data class DivisionOfValues(
    val dividend: StatisticBasedValue,
    val divisor: StatisticBasedValue,
) : StatisticBasedValue()