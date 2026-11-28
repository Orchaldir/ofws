package at.orchaldir.ofws.ui.utils

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import at.orchaldir.ofws.model.utils.*
import at.orchaldir.ofws.model.utils.name.NotEmptyString
import at.orchaldir.ofws.utils.SelectNotEmptyString
import at.orchaldir.ofws.utils.SelectValue
import at.orchaldir.ofws.utils.ShowDetails
import at.orchaldir.ofws.utils.doNothing

// display

@Composable
fun DisplayUnitOfMeasurement(
    unit: UnitOfMeasurement?,
    showUnitless: Boolean = true,
) {
    when (unit) {
        is PrefixedUnit -> Text("Prefix '${unit.prefix.text}'")
        is SuffixedUnit -> Text("Suffix '${unit.suffix.text}'")
        Unitless -> if (showUnitless) {
            Text("Unitless")
        }

        null -> doNothing()
    }
}

// edit

// edit

@Composable
fun EditUnitOfMeasurement(
    unit: UnitOfMeasurement,
    label: String = "Unit",
    onChange: (UnitOfMeasurement) -> Unit,
) {
    var type by remember { mutableStateOf(unit.getType()) }
    var text by remember {
        val value = when (unit) {
            is PrefixedUnit -> unit.prefix
            is SuffixedUnit -> unit.suffix
            Unitless -> NotEmptyString.init("Unit")
        }
        mutableStateOf(value)
    }
    val update: () -> Unit = {
        val data = when (type) {
            UnitOfMeasurementType.Prefix -> PrefixedUnit(text)
            UnitOfMeasurementType.Suffix -> SuffixedUnit(text)
            UnitOfMeasurementType.Unitless -> Unitless
        }

        onChange(data)
    }

    ShowDetails(label) {
        SelectValue(UnitOfMeasurementType.entries, type) {
            type = it
            update()
        }

        when (type) {
            UnitOfMeasurementType.Prefix, UnitOfMeasurementType.Suffix -> SelectNotEmptyString(
                type.name,
                text,
            ) {
                text = it
                update()
            }

            UnitOfMeasurementType.Unitless -> doNothing()
        }
    }
}
