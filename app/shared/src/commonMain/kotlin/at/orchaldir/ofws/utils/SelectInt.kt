package at.orchaldir.ofws.utils

import androidx.compose.foundation.layout.Row
import androidx.compose.material3.*
import androidx.compose.runtime.*

@Composable
fun SelectInt(
    start: Int,
    label: String,
    min: Int,
    max: Int,
    onChange: (Int) -> Unit,
) {
    var current by remember { mutableFloatStateOf(start.toFloat()) }

    Row {
        Text(text = label)
        Slider(
            value = current,
            onValueChange = {
                current = it
                onChange(current.toInt())
            },
            steps = (max - min),
            valueRange = min.toFloat()..max.toFloat()
        )
        Text(text = current.toInt().toString())
    }
}