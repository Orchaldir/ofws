package at.orchaldir.ofws.ui.rpg

import androidx.compose.runtime.*
import at.orchaldir.ofws.model.rpg.statistic.Attribute
import at.orchaldir.ofws.model.rpg.statistic.DerivedAttribute
import at.orchaldir.ofws.model.rpg.statistic.Skill
import at.orchaldir.ofws.model.rpg.statistic.StatisticData
import at.orchaldir.ofws.model.rpg.statistic.StatisticDataType
import at.orchaldir.ofws.utils.SelectValue

// display

// edit

@Composable
fun EditStatisticData(
    data: StatisticData,
    onChange: (StatisticData) -> Unit,
) {
    var type by remember { mutableStateOf(data.getType()) }
    var cost by remember { mutableStateOf(data.cost()) }
    var baseValue by remember { mutableStateOf(data.baseValue()) }
    val update: () -> Unit = {
        val data = when (type) {
            StatisticDataType.Attribute -> Attribute(
                baseValue,
                cost,
            )
            StatisticDataType.DerivedAttribute -> DerivedAttribute(
                baseValue,
                cost,
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
    EditStatisticBasedValue("Base Value", baseValue) {
        baseValue = it
        update()
    }
    EditStatisticCost("Cost", data.cost()) {
        cost = it
        update()
    }
}