package at.orchaldir.ofws.ui.rpg

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import at.orchaldir.ofws.model.rpg.statistic.FixedStatisticCost
import at.orchaldir.ofws.model.rpg.statistic.GurpsSkillCost
import at.orchaldir.ofws.model.rpg.statistic.StatisticCost
import at.orchaldir.ofws.model.rpg.statistic.UndefinedStatisticCost

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
