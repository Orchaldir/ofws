package at.orchaldir.ofws.ui.rpg

import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import at.orchaldir.ofws.model.rpg.statistic.Attribute
import at.orchaldir.ofws.model.rpg.statistic.DerivedAttribute
import at.orchaldir.ofws.model.rpg.statistic.FixedStatisticCost
import at.orchaldir.ofws.model.rpg.statistic.GurpsSkillCost
import at.orchaldir.ofws.model.rpg.statistic.Skill
import at.orchaldir.ofws.model.rpg.statistic.StatisticCost
import at.orchaldir.ofws.model.rpg.statistic.StatisticCostType
import at.orchaldir.ofws.model.rpg.statistic.StatisticCostType.*
import at.orchaldir.ofws.model.rpg.statistic.StatisticData
import at.orchaldir.ofws.model.rpg.statistic.StatisticDataType
import at.orchaldir.ofws.model.rpg.statistic.UndefinedStatisticCost
import at.orchaldir.ofws.utils.SelectInt
import at.orchaldir.ofws.utils.SelectValue

@Composable
fun DisplayStatisticCost(
    cost: StatisticCost,
    showUndefined: Boolean = true,
) {
    val text = when (cost) {
        is FixedStatisticCost -> "${cost.cost} per Point"
        GurpsSkillCost -> "Gurps Skill Cost"
        UndefinedStatisticCost -> if (showUndefined) {
            "Undefined"
        } else {
            return
        }
    }

    Text(text)
}

@Composable
fun EditStatisticCost(
    cost: StatisticCost,
    onChange: (StatisticCost) -> Unit,
) {
    var type by remember { mutableStateOf(cost.getType()) }
    var cost by remember {
        val value = when (cost) {
            is FixedStatisticCost -> cost.cost
            else -> 1
        }
        mutableStateOf(value)
    }
    val update: () -> Unit = {
        val data = when (type) {
            Fixed -> FixedStatisticCost(cost)
            GurpsSkill -> GurpsSkillCost
            Undefined -> UndefinedStatisticCost
        }

        onChange(data)
    }

    SelectValue(StatisticCostType.entries, type) {
        type = it
        update()
    }

    if (type == Fixed) {
        SelectInt(1, 10, cost) {
            cost = it
        }
    }
}
