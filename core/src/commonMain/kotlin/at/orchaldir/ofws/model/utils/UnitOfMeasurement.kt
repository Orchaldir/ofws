package at.orchaldir.ofws.model.utils

import at.orchaldir.ofws.model.utils.name.NotEmptyString
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

enum class UnitOfMeasurementType {
    Prefix,
    Suffix,
    Unitless,
}

@Serializable
sealed class UnitOfMeasurement {

    fun getType() = when (this) {
        is PrefixedUnit -> UnitOfMeasurementType.Prefix
        is SuffixedUnit -> UnitOfMeasurementType.Suffix
        Unitless -> UnitOfMeasurementType.Unitless
    }

    fun display(value: Int) = when (this) {
        is PrefixedUnit -> "${prefix.text} $value"
        is SuffixedUnit -> "$value ${suffix.text}"
        Unitless -> value.toString()
    }
}

@Serializable
@SerialName("Prefix")
data class PrefixedUnit(
    val prefix: NotEmptyString,
) : UnitOfMeasurement()

@Serializable
@SerialName("Suffix")
data class SuffixedUnit(
    val suffix: NotEmptyString,
) : UnitOfMeasurement()

@Serializable
@SerialName("Unitless")
data object Unitless : UnitOfMeasurement()