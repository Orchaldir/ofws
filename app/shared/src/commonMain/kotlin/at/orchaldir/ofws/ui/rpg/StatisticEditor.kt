package at.orchaldir.ofws.ui.rpg

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import at.orchaldir.ofws.model.rpg.statistic.Attribute
import at.orchaldir.ofws.model.rpg.statistic.Statistic
import at.orchaldir.ofws.model.rpg.statistic.StatisticData
import at.orchaldir.ofws.model.rpg.statistic.StatisticId
import at.orchaldir.ofws.model.utils.name.Name
import at.orchaldir.ofws.network.StatisticClient
import at.orchaldir.ofws.utils.SelectName
import at.orchaldir.ofws.utils.SpacedColumn
import at.orchaldir.ofws.utils.SpacedRow
import io.ktor.client.HttpClient
import kotlinx.coroutines.launch

@Composable
fun StatisticEditor(
    client: HttpClient,
    id: StatisticId,
    onBack: () -> Unit,
) {
    val statisticClient = StatisticClient(client)
    val scope = rememberCoroutineScope()
    var others by remember { mutableStateOf(emptyList<Statistic>()) }
    var name by remember { mutableStateOf(Name.init(id)) }
    var data by remember { mutableStateOf<StatisticData>(Attribute()) }

    LaunchedEffect(id) {
        val statistics = statisticClient.getAllStatistics()
        val statistic = statistics.first { it.id == id }

        others = statistics.filter { it.id != id }
        name = statistic.name
        data = statistic.data
    }

    SpacedColumn(modifier = Modifier.padding(20.dp)) {
        Text("Update ${name.text}", fontSize = 20.sp)

        SelectName(name) {
            name = it
        }

        EditStatisticData(others, data) {
            data = it
        }

        SpacedRow {
            OutlinedButton(onClick = {
                scope.launch {
                    val newStatistic = Statistic(
                        id,
                        name,
                        data,
                    )

                    statisticClient.updateStatistic(newStatistic)

                    onBack()
                }
            }) {
                Text("Update")
            }
            OutlinedButton(onClick = onBack) {
                Text("Cancel")
            }
        }
    }
}