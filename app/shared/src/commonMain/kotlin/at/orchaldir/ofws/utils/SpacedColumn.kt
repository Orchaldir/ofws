package at.orchaldir.ofws.utils

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun SpacedColumn(
    modifier: Modifier = Modifier,
    spacedBy: Dp = 10.dp,
    content: @Composable () -> Unit,
) = Column(
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(spacedBy),
) {
    content()
}

@Composable
fun SpacedLazyColumn(
    modifier: Modifier = Modifier,
    spacedBy: Dp = 10.dp,
    content: LazyListScope.() -> Unit,
) = LazyColumn (
    modifier = modifier.fillMaxWidth(),
    horizontalAlignment = Alignment.CenterHorizontally,
    verticalArrangement = Arrangement.spacedBy(spacedBy),
) {
    content()
}