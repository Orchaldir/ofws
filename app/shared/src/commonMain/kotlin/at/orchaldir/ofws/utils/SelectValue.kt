package at.orchaldir.ofws.utils

import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun <T : Enum<T>> SelectValue(
    values: List<T>,
    current: T,
    onValueChange: (T) -> Unit,
) {
    var selectedIndex by remember { mutableStateOf(current.ordinal) }

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
                label = { Text(value.name) }
            )
        }
    }
}