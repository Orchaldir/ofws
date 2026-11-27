package at.orchaldir.ofws.ui.rpg

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import at.orchaldir.ofws.model.STATISTIC_ID_0
import at.orchaldir.ofws.model.rpg.statistic.*
import at.orchaldir.ofws.utils.EditList
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
    statistics: List<Statistic>,
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
                is ProductOfValues -> value.values
                is SumOfValues -> value.values
                else -> listOf(defaultValue, defaultValue)
            }
        )
    }
    var statisticId by remember {
        mutableStateOf(
            when (value) {
                is StatisticLookup -> value.statistic
                else -> statistics.firstOrNull()?.id ?: STATISTIC_ID_0
            }
        )
    }
    val update: () -> Unit = {
        val data = when (type) {
            StatisticBasedValueType.Lookup -> StatisticLookup(statisticId)
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
                EditStatisticBasedValue(statistics, dividend, "Dividend") {
                    dividend = it
                    update()
                }
                EditStatisticBasedValue(statistics, divisor, "Divisor") {
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

            StatisticBasedValueType.Lookup -> {
                SelectValue(
                    statistics,
                    statisticId,
                ) {
                    statisticId = it
                    update()
                }
            }
            StatisticBasedValueType.Product, StatisticBasedValueType.Sum -> {
                EditList(
                    values,
                    2,
                    10,
                    { updatedList ->
                        values = updatedList
                        update()
                    },
                    {
                        defaultValue
                    },
                ) { entry, updateEntry ->
                    EditStatisticBasedValue(statistics, entry) { updatedEntry ->
                        updateEntry(updatedEntry)
                    }
                }
            }
        }
    }
}