package at.orchaldir.ofws.utils

import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun <ID : Id, ELEMENT : Element<ID>> SelectValue(
    values: List<ELEMENT>,
    id: ID,
    onValueChange: (ID) -> Unit,
) = SelectValue(
    values,
    values.indexOfFirst { it.id() == id },
    { it.name().text },
) {
    onValueChange(it.id())
}

@Composable
fun <T : Enum<T>> SelectValue(
    values: List<T>,
    current: T,
    onValueChange: (T) -> Unit,
) = SelectValue(
    values,
    current.ordinal,
    { it.nameWithSpaces() },
    onValueChange,
)

@Composable
fun <T> SelectValue(
    values: List<T>,
    currentIndex: Int,
    getName: (T) -> String,
    onValueChange: (T) -> Unit,
) {
    var selectedIndex by remember { mutableStateOf(currentIndex) }

    SingleChoiceSegmentedButtonRow {
        values.forEachIndexed { index, value ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = values.size
                ),
                onClick = {
                    selectedIndex = index
                    onValueChange(values[index])
                },
                selected = index == selectedIndex,
                label = { Text(getName(value)) }
            )
        }
    }
}