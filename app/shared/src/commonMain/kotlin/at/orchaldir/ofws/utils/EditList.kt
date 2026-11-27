package at.orchaldir.ofws.utils

import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.*

@Composable
fun <T> EditList(
    values: List<T>,
    minSize: Int,
    maxSize: Int,
    onChange: (List<T>) -> Unit,
    editEntry: @Composable (T,(T) -> Unit) -> Unit
) {
    var current by remember { mutableStateOf(values) }
    val canDelete = current.size > minSize

    current.withIndex().forEach { (index, value) ->
        Row {
            DeleteButton {
                val mutable = current.toMutableStateList()
                mutable.removeAt(index)

                current = mutable
            }

            editEntry(value) {
                val mutable = current.toMutableList()
                mutable[index] = it

                current = mutable
                onChange(current)
            }
        }
    }
}