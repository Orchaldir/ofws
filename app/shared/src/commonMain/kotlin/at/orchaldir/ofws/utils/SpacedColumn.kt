package at.orchaldir.ofws.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SpacedColumn(
    modifier: Modifier = Modifier,
    spacedBy: Dp = 10.dp,
    content: @Composable () -> Unit,
) = Column(
    modifier = modifier,
    verticalArrangement = Arrangement.spacedBy(spacedBy),
) {
    content()
}