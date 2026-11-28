package at.orchaldir.ofws.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SpacedRow(
    modifier: Modifier = Modifier,
    spacedBy: Dp = 10.dp,
    content: @Composable () -> Unit,
) = Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(spacedBy),
    verticalAlignment = Alignment.CenterVertically,
) {
    content()
}