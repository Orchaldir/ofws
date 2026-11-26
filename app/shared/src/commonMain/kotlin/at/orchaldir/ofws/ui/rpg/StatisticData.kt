package at.orchaldir.ofws.ui.rpg

import androidx.compose.runtime.*
import at.orchaldir.ofws.model.rpg.statistic.Attribute
import at.orchaldir.ofws.model.rpg.statistic.DerivedAttribute
import at.orchaldir.ofws.model.rpg.statistic.Skill
import at.orchaldir.ofws.model.rpg.statistic.StatisticData
import at.orchaldir.ofws.model.rpg.statistic.StatisticDataType
import at.orchaldir.ofws.utils.SelectValue

@Composable
fun EditStatisticData(
    data: StatisticData,
    onChange: (StatisticData) -> Unit,
) {
    var type by remember { mutableStateOf(data.getType()) }
    var cost by remember { mutableStateOf(data.cost()) }
    val update: () -> Unit = {
        val data = when (type) {
            StatisticDataType.Attribute -> Attribute(
                cost = cost,
            )
            StatisticDataType.DerivedAttribute -> DerivedAttribute(
                cost = cost,
            )
            StatisticDataType.Skill -> Skill(
                cost = cost,
            )
        }

        onChange(data)
    }

    SelectValue(StatisticDataType.entries, type) {
        type = it
        update()
    }
    EditStatisticCost(data.cost()) {
        cost = it
        update()
    }
}