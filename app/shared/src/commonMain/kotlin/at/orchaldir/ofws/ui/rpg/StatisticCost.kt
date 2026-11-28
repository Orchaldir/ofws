package at.orchaldir.ofws.ui.rpg

import androidx.compose.material3.Text
import androidx.compose.runtime.*
import at.orchaldir.ofws.model.rpg.statistic.*
import at.orchaldir.ofws.model.rpg.statistic.StatisticCostType.*
import at.orchaldir.ofws.utils.SelectInt
import at.orchaldir.ofws.utils.SelectValue
import at.orchaldir.ofws.utils.ShowDetails

// display

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

// edit

@Composable
fun EditStatisticCost(
    label: String,
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

    ShowDetails(label) {
        SelectValue(StatisticCostType.entries, type) {
            type = it
            update()
        }

        if (type == Fixed) {
            SelectInt(cost, "Cost per Level", 1, 10) {
                cost = it
                update()
            }
        }
    }
}
