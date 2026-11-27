package at.orchaldir.ofws.utils

import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*

@Composable
fun <T> EditList(
    values: List<T>,
    minSize: Int,
    maxSize: Int,
    onChange: (List<T>) -> Unit,
    addValue: (Int) -> T,
    editValue: @Composable (T, (T) -> Unit) -> Unit
) {
    val current = remember { values.withIndex().toList().toMutableStateList() }
    var next by remember { mutableStateOf(values.size) }
    val canDelete = current.size > minSize

    SpacedLazyColumn {
        itemsIndexed(
            current,
            { _, entry -> entry.index }
        ) { index, entry ->
            SpacedRow {
                DeleteButton(canDelete) {
                    current.removeAt(index)

                    onChange(current.map { it.value })
                }

                editValue(entry.value) {
                    current[index] = IndexedValue(entry.index, it)

                    onChange(current.map { it.value })
                }
            }
        }
    }

    if (current.size < maxSize) {
        OutlinedButton(onClick = {
            val newValue = addValue(current.size)
            current += IndexedValue(next++, newValue)

            onChange(current.map { it.value })
        }) {
            Text("Add")
        }
    }
}