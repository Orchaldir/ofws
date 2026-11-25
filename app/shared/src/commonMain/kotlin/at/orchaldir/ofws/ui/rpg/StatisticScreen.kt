package at.orchaldir.ofws.ui.rpg

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Delete
import androidx.compose.material.icons.rounded.Edit
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import at.orchaldir.ofws.model.rpg.statistic.Statistic
import at.orchaldir.ofws.model.user.User
import at.orchaldir.ofws.network.rpg.StatisticApi
import at.orchaldir.ofws.ui.UserCard
import at.orchaldir.ofws.ui.UserUpdateDialog
import at.orchaldir.ofws.ui.utils.DisplayUnitOfMeasurement
import com.mamon.tablekmp.component.Table
import com.mamon.tablekmp.model.TableColors
import com.mamon.tablekmp.model.TableColumn
import io.ktor.client.*
import io.ktor.websocket.Frame
import kotlinx.coroutines.launch

@Composable
fun StatisticScreen(
    client: HttpClient,
) {
    val statisticApi = remember { StatisticApi(client) }
    var statistics by remember { mutableStateOf(emptyList<Statistic>()) }
    var currentStatistic by remember { mutableStateOf<Statistic?>(null) }
    val scope = rememberCoroutineScope()
    val columns = listOf<TableColumn<Statistic>>(
        TableColumn("Name") { Text(it.name.text) },
        TableColumn("Type") { Text(it.data.getType().name) },
        TableColumn("Cost") { DisplayStatisticCost(it.data.cost(), false) },
        TableColumn("Base Value") { DisplayStatisticBasedValue(it.data.baseValue()) },
        TableColumn("Unit") { DisplayUnitOfMeasurement(it.data.unit(), false) },
        TableColumn("Actions") { statistic ->
            Row {
                IconButton(
                    onClick = {
                        scope.launch {
                            currentStatistic = statistic
                        }
                    },
                ) {
                    Icon(painter = rememberVectorPainter(Icons.Rounded.Edit), "Edit")
                }
                IconButton(
                    onClick = {
                        scope.launch {
                            statisticApi.deleteStatistic(statistic)
                            statistics = statisticApi.getAllStatistics()
                        }
                    },
                ) {
                    Icon(painter = rememberVectorPainter(Icons.Rounded.Delete), "Delete")
                }
            }
        },
    )

    LaunchedEffect(Unit) {
        statistics = statisticApi.getAllStatistics()
    }

    if (currentStatistic != null) {
        StatisticUpdateDialog(
            currentStatistic!!,
            onConfirm = { newStatistic ->
                scope.launch {
                    statisticApi.updateStatistic(newStatistic)
                    statistics = statisticApi.getAllStatistics()
                }
                currentStatistic = null
            }
        )
    }

    Column {
        FloatingActionButton(
            onClick = {
                scope.launch {
                    val id = statisticApi.createStatistic()
                    statistics = statisticApi.getAllStatistics()
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