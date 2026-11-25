package at.orchaldir.ofws.ui.rpg

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import at.orchaldir.ofws.model.rpg.statistic.*

@Composable
fun DisplayStatisticBasedValue(
    value: StatisticBasedValue,
    isTopLevel: Boolean = true,
) {
    when (value) {
        is DivisionOfValues -> brackets(isTopLevel) {
            DisplayStatisticBasedValue(value.dividend, false)
            Text(" / ")
            DisplayStatisticBasedValue(value.divisor, false)
        }
        is FixedNumber -> Text(value.default.toString())
        is ProductOfValues -> TODO()
        is StatisticLookup -> TODO()
        is SumOfValues -> TODO()
    }
}

@Composable
private fun brackets(
    isTopLevel: Boolean,
    content: @Composable () -> Unit,
) {
    if (isTopLevel) {
        content()
    } else {
        Text("(")
        content()
        Text(")")
    }
}