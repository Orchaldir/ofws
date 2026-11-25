package at.orchaldir.ofws.ui.rpg

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import at.orchaldir.ofws.model.rpg.statistic.*
import kotlin.collections.component1
import kotlin.collections.component2
import kotlin.compareTo

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
        is DivisionOfValues -> DisplayValues(listOf(value.dividend, value.divisor), "/", isTopLevel)
        is FixedNumber -> Text(value.default.toString())
        is ProductOfValues -> DisplayValues(value.values, "*", isTopLevel)
        is StatisticLookup -> TODO()
        is SumOfValues -> DisplayValues(value.values, "+", isTopLevel)
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

@Composable
private fun DisplayValues(
    values: List<StatisticBasedValue>,
    symbol: String,
    isTopLevel: Boolean,
) {
    Brackets(isTopLevel) {
        values.withIndex().forEach { (i, value) ->
            if (i > 0) {
                Text(" $symbol ")
            }
            InternalStatisticBasedValue(value, false)
        }
    }
}