package at.orchaldir.ofws.ui.rpg

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import at.orchaldir.ofws.model.rpg.statistic.Statistic
import at.orchaldir.ofws.network.StatisticClient
import at.orchaldir.ofws.ui.utils.DisplayUnitOfMeasurement
import at.orchaldir.ofws.utils.DeleteButton
import at.orchaldir.ofws.utils.EditButton
import at.orchaldir.ofws.utils.SpacedColumn
import com.mamon.tablekmp.component.Table
import com.mamon.tablekmp.model.TableColors
import com.mamon.tablekmp.model.TableColumn
import io.ktor.client.*
import kotlinx.coroutines.launch

@Composable
fun StatisticScreen(
    client: HttpClient,
    onEdit: (Statistic) -> Unit,
) {
    val statisticClient = remember { StatisticClient(client) }
    var statistics by remember { mutableStateOf(emptyList<Statistic>()) }
    val scope = rememberCoroutineScope()
    val columns = listOf<TableColumn<Statistic>>(
        TableColumn("Name") { Text(it.name.text) },
        TableColumn("Type") { Text(it.data.getType().name) },
        TableColumn("Cost") { DisplayStatisticCost(it.data.cost(), false) },
        TableColumn("Base Value") { DisplayStatisticBasedValue(statistics, it.data.baseValue()) },
        TableColumn("Unit") { DisplayUnitOfMeasurement(it.data.unit(), false) },
        TableColumn("Actions") { statistic ->
            Row {
                EditButton {
                    scope.launch {
                        onEdit(statistic)
                    }
                }
                DeleteButton {
                    scope.launch {
                        statisticClient.deleteStatistic(statistic)
                        statistics = statisticClient.getAllStatistics()
                    }
                }
            }
        },
    )

    LaunchedEffect(Unit) {
        statistics = statisticClient.getAllStatistics()
    }

    SpacedColumn {
        FloatingActionButton(
            onClick = {
                scope.launch {
                    val id = statisticClient.createStatistic()
                    statistics = statisticClient.getAllStatistics()
                }
            },
        ) {
            Icon(painter = rememberVectorPainter(Icons.Rounded.Add), "Add User")
        }

        Table(
            items = statistics,
            columns = columns,
            colors = TableColors(
                headerContainerColor = Color.Gray,
            ),
        )
    }
}