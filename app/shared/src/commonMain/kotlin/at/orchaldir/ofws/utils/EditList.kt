package at.orchaldir.ofws.utils

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

    current.withIndex().forEach { (index,value) ->
        editEntry(value) {
            val mutable = current.toMutableList()
            mutable[index] = it

            current = mutable
            onChange(current)
        }
    }
}