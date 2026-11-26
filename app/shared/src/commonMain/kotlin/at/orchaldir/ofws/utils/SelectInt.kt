package at.orchaldir.ofws.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion

@Composable
fun SelectInt(
    start: Int,
    label: String,
    min: Int,
    max: Int,
    onChange: (Int) -> Unit,
) {
    var current by remember { mutableFloatStateOf(start.toFloat()) }

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(text = "$label:")
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