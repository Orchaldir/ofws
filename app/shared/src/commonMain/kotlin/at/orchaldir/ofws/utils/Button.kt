package at.orchaldir.ofws.utils

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.vector.rememberVectorPainter

@Composable
fun DeleteButton(
    enabled: Boolean = true,
    onClick: () -> Unit,
) = IconButton(
    enabled = enabled,
    onClick = onClick,
) {
    Icon(painter = rememberVectorPainter(Icons.Rounded.Delete), "Delete")
}