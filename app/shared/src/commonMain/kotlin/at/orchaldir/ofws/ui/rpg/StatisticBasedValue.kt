package at.orchaldir.ofws.ui.rpg

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import at.orchaldir.ofws.model.rpg.statistic.*
import at.orchaldir.ofws.utils.SelectInt
import at.orchaldir.ofws.utils.SelectValue
import at.orchaldir.ofws.utils.ShowDetails
import at.orchaldir.ofws.utils.doNothing

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
    label: String? = null,
    onChange: (StatisticBasedValue) -> Unit,
) {
    val defaultValue = FixedNumber(1)
    var type by remember { mutableStateOf(value.getType()) }
    var fixedNumber by remember {
        mutableStateOf(
            when (value) {
                is FixedNumber -> value.default
                else -> 0
            }
        )
    }
    var dividend by remember {
        mutableStateOf(
            when (value) {
                is DivisionOfValues -> value.dividend
                else -> defaultValue
            }
        )
    }
    var divisor by remember {
        mutableStateOf(
            when (value) {
                is DivisionOfValues -> value.divisor
                else -> defaultValue
            }
        )
    }
    var values by remember {
        mutableStateOf(
            when (value) {
                is ProductOfValues -> value.values.toMutableStateList()
                is SumOfValues -> value.values.toMutableStateList()
                else -> listOf(defaultValue, defaultValue)
            }
        )
    }
    val update: () -> Unit = {
        val data = when (type) {
            StatisticBasedValueType.Lookup -> defaultValue
            StatisticBasedValueType.Division -> DivisionOfValues(dividend, divisor)
            StatisticBasedValueType.FixedNumber -> FixedNumber(fixedNumber)
            StatisticBasedValueType.Product -> ProductOfValues(values)
            StatisticBasedValueType.Sum -> SumOfValues(values)
        }

        onChange(data)
    }

    ShowDetails(label) {
        SelectValue(StatisticBasedValueType.entries, type) {
            type = it
            update()
        }

        when (type) {
            StatisticBasedValueType.Division -> {
                EditStatisticBasedValue(dividend, "Dividend") {
                    dividend = it
                    update()
                }
                EditStatisticBasedValue(divisor, "Divisor") {
                    divisor = it
                    update()
                }
            }
            StatisticBasedValueType.FixedNumber -> {
                SelectInt(fixedNumber, "Default", -100, 100) {
                    fixedNumber = it
                    update()
                }
            }

            StatisticBasedValueType.Lookup -> doNothing()
            StatisticBasedValueType.Product, StatisticBasedValueType.Sum -> {
                values.withIndex().forEach { (index,entry) ->
                    EditStatisticBasedValue(entry) {
                        val mutable = values.toMutableList()
                        mutable[index] = entry

                        values = mutable
                        update()
                    }
                }
            }
        }
    }
}