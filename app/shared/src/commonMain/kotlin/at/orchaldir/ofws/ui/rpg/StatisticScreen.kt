package at.orchaldir.ofws.ui.rpg

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.rememberVectorPainter
import androidx.compose.ui.text.font.FontWeight
import at.orchaldir.ofws.model.rpg.statistic.Statistic
import at.orchaldir.ofws.network.rpg.StatisticApi
import at.orchaldir.ofws.ui.UserCard
import at.orchaldir.ofws.ui.UserUpdateDialog
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
    val scope = rememberCoroutineScope()
    val columns = listOf(
        TableColumn<Statistic>("Name") { Text(it.name.text) },
        TableColumn<Statistic>("Type") { Text(it.data.getType().name) },
        TableColumn<Statistic>("Base Value") { DisplayStatisticBasedValue(it.data.baseValue()) },
    )

    LaunchedEffect(Unit) {
        statistics = statisticApi.getAllStatistics()
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