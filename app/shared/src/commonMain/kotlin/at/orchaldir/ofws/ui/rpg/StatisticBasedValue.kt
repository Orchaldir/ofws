package at.orchaldir.ofws.ui.rpg

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import at.orchaldir.ofws.model.rpg.statistic.*

@Composable
fun DisplayStatisticBasedValue(
    value: StatisticBasedValue,
) {
    Row {
        InternalStatisticBasedValue(value, true)
    }
}

@Composable
private fun InternalStatisticBasedValue(
    value: StatisticBasedValue,
    isTopLevel: Boolean = true,
) {
    when (value) {
        is DivisionOfValues -> Brackets(isTopLevel) {
            InternalStatisticBasedValue(value.dividend, false)
            Text(" / ")
            InternalStatisticBasedValue(value.divisor, false)
        }

        is FixedNumber -> Text(value.default.toString())
        is ProductOfValues -> TODO()
        is StatisticLookup -> TODO()
        is SumOfValues -> TODO()
    }
}

 @Composable
private fun Brackets(
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