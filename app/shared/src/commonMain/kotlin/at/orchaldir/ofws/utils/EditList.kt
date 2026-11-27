package at.orchaldir.ofws.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier

@Composable
fun <T> EditList(
    values: List<T>,
    minSize: Int,
    maxSize: Int,
    onChange: (List<T>) -> Unit,
    addValue: (Int) -> T,
    editValue: @Composable (T, (T) -> Unit) -> Unit
) {
    val current = remember { values.toMutableStateList() }
    val canDelete = current.size > minSize

    current.withIndex().forEach { (index, value) ->
        SpacedRow {
            DeleteButton(canDelete) {
                current.removeAt(index)

                onChange(current)
            }

            editValue(value) {
                current[index] = it

                onChange(current)
            }
        }
    }

    if (current.size < maxSize) {
        OutlinedButton(onClick = {
            val newValue = addValue(current.size)
            current += newValue

            onChange(current)
        }) {
            Text("Add")
        }
    }
}