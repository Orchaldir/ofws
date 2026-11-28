package at.orchaldir.ofws.utils

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun SelectInt(
    start: Int,
    label: String,
    min: Int,
    max: Int,
    onChange: (Int) -> Unit,
) {
    var current by remember { mutableFloatStateOf(start.toFloat()) }
    val boxWidth = Modifier.width(10.dp)

    Row(
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            text = "$label:",
            fontWeight = FontWeight.Bold,
        )
        Box(modifier = boxWidth)
        Text(text = current.toInt().toString())
        Box(modifier = boxWidth)
        Slider(
            value = current,
            onValueChange = {
                current = it
                onChange(current.toInt())
            },
            steps = (max - min - 1),
            valueRange = min.toFloat()..max.toFloat()
        )
    }
}