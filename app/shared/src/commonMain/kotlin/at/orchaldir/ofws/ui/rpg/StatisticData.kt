package at.orchaldir.ofws.ui.rpg

import androidx.compose.runtime.*
import at.orchaldir.ofws.model.rpg.statistic.Attribute
import at.orchaldir.ofws.model.rpg.statistic.DerivedAttribute
import at.orchaldir.ofws.model.rpg.statistic.DivisionOfValues
import at.orchaldir.ofws.model.rpg.statistic.Skill
import at.orchaldir.ofws.model.rpg.statistic.Statistic
import at.orchaldir.ofws.model.rpg.statistic.StatisticData
import at.orchaldir.ofws.model.rpg.statistic.StatisticDataType
import at.orchaldir.ofws.model.utils.Unitless
import at.orchaldir.ofws.ui.utils.EditUnitOfMeasurement
import at.orchaldir.ofws.utils.SelectValue
import io.ktor.openapi.ReferenceOr.Companion.value

// display

// edit

@Composable
fun EditStatisticData(
    statistics: List<Statistic>,
    data: StatisticData,
    onChange: (StatisticData) -> Unit,
) {
    var type by remember { mutableStateOf(data.getType()) }
    var cost by remember { mutableStateOf(data.cost()) }
    var baseValue by remember { mutableStateOf(data.baseValue()) }
    var unit by remember {
        mutableStateOf(
            when (data) {
                is DerivedAttribute -> data.unit
                else -> Unitless
            }
        )
    }
    val update: () -> Unit = {
        val data = when (type) {
            StatisticDataType.Attribute -> Attribute(
                baseValue,
                cost,
            )
            StatisticDataType.DerivedAttribute -> DerivedAttribute(
                baseValue,
                cost,
                unit,
            )
            StatisticDataType.Skill -> Skill(
                baseValue,
                cost,
            )
        }

        onChange(data)
    }

    SelectValue(StatisticDataType.entries, type) {
        type = it
        update()
    }
    EditStatisticBasedValue(statistics, baseValue, "Base Value") {
        baseValue = it
        update()
    }
    EditStatisticCost("Cost", data.cost()) {
        cost = it
        update()
    }

    if (type == StatisticDataType.DerivedAttribute) {
        EditUnitOfMeasurement(unit) {
            unit = it
            update()
        }
    }
}