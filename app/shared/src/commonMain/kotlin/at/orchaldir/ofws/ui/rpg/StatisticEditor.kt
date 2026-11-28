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
import at.orchaldir.ofws.network.rpg.StatisticApi
import at.orchaldir.ofws.utils.SelectName
import at.orchaldir.ofws.utils.SpacedColumn
import io.ktor.client.HttpClient
import kotlinx.coroutines.launch

@Composable
fun StatisticEditor(
    client: HttpClient,
    id: StatisticId,
) {
    val statisticApi = remember { StatisticApi(client) }
    val scope = rememberCoroutineScope()
    var others by remember { mutableStateOf(emptyList<Statistic>()) }
    var statistic by remember { mutableStateOf(Statistic(id)) }
    var name by remember { mutableStateOf(Name.init(id)) }
    var data by remember { mutableStateOf<StatisticData>(Attribute()) }

    LaunchedEffect(Unit) {
        val statistics = statisticApi.getAllStatistics()

        others = statistics.filter { it.id != id }
        statistic = statistics.first { it.id == id }
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

        OutlinedButton(onClick = {
            scope.launch {
                val newStatistic = statistic.copy(
                    name = name,
                    data = data,
                )
                statisticApi.updateStatistic(newStatistic)
            }
        }) {
            Text("Update")
        }
    }
}