package at.orchaldir.ofws.utils

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun ShowDetails(
    label: String,
    color: Color = Color.Black,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = Modifier.border(2.dp, color, RoundedCornerShape(16.dp))
    ) {
        Column(Modifier.padding(12.dp)) {
            Text(label)
            Spacer(modifier = Modifier.size(10.dp))
            content()
        }
    }
}