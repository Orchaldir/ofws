package at.orchaldir.ofws.ui.rpg

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import at.orchaldir.ofws.model.rpg.statistic.*
import at.orchaldir.ofws.utils.SelectInt
import at.orchaldir.ofws.utils.SelectValue
import at.orchaldir.ofws.utils.doNothing
import kotlinx.coroutines.selects.select

// display

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

// edit

@Composable
fun EditStatisticBasedValue(
    value: StatisticBasedValue,
    onChange: (StatisticBasedValue) -> Unit,
) {
    var type by remember { mutableStateOf(value.getType()) }
    var fixedNumber by remember {
        mutableStateOf(
            when (value) {
                is FixedNumber -> value.default
                else -> 0
            }
        )
    }
    val update: () -> Unit = {
        val data = when (type) {
            StatisticBasedValueType.Lookup -> FixedNumber(fixedNumber)
            StatisticBasedValueType.Division -> FixedNumber(fixedNumber)
            StatisticBasedValueType.FixedNumber -> FixedNumber(fixedNumber)
            StatisticBasedValueType.Product -> FixedNumber(fixedNumber)
            StatisticBasedValueType.Sum -> FixedNumber(fixedNumber)
        }

        onChange(data)
    }

    SelectValue(StatisticBasedValueType.entries, type) {
        type = it
        update()
    }

    when (type) {
        StatisticBasedValueType.Division -> doNothing()
        StatisticBasedValueType.FixedNumber -> {
            SelectInt(fixedNumber, "Default", -100, 100) {
                fixedNumber = it
                update()
            }
        }

        StatisticBasedValueType.Lookup -> doNothing()
        StatisticBasedValueType.Product -> doNothing()
        StatisticBasedValueType.Sum -> doNothing()
    }
}