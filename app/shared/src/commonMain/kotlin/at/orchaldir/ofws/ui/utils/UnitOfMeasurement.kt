package at.orchaldir.ofws.ui.utils

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import at.orchaldir.ofws.model.rpg.statistic.FixedStatisticCost
import at.orchaldir.ofws.model.rpg.statistic.GurpsSkillCost
import at.orchaldir.ofws.model.rpg.statistic.StatisticCost
import at.orchaldir.ofws.model.rpg.statistic.StatisticCostType
import at.orchaldir.ofws.model.rpg.statistic.StatisticCostType.Fixed
import at.orchaldir.ofws.model.rpg.statistic.StatisticCostType.GurpsSkill
import at.orchaldir.ofws.model.rpg.statistic.StatisticCostType.Undefined
import at.orchaldir.ofws.model.rpg.statistic.UndefinedStatisticCost
import at.orchaldir.ofws.model.utils.PrefixedUnit
import at.orchaldir.ofws.model.utils.SuffixedUnit
import at.orchaldir.ofws.model.utils.UnitOfMeasurement
import at.orchaldir.ofws.model.utils.UnitOfMeasurementType
import at.orchaldir.ofws.model.utils.Unitless
import at.orchaldir.ofws.model.utils.name.NotEmptyString
import at.orchaldir.ofws.utils.SelectInt
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
