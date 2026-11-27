package at.orchaldir.ofws.ui.rpg

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CornerSize
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import at.orchaldir.ofws.model.rpg.statistic.Statistic
import at.orchaldir.ofws.model.utils.name.Name
import at.orchaldir.ofws.utils.SelectName
import at.orchaldir.ofws.utils.SpacedColumn

@Composable
fun StatisticUpdateDialog(
    statistic: Statistic,
    onConfirm: (Statistic) -> Unit,
) {
    var name by remember { mutableStateOf(statistic.name) }
    var data by remember { mutableStateOf(statistic.data) }

    Dialog(onDismissRequest = {}) {
        Card(
            modifier = Modifier.fillMaxWidth().padding(4.dp),
            shape = RoundedCornerShape(CornerSize(4.dp))
        ) {
            SpacedColumn(modifier = Modifier.padding(20.dp)) {
                Text("Update ${statistic.name.text}", fontSize = 20.sp)
                SelectName(statistic.name) {
                    name = it
                }
                EditStatisticData(statistic.data) {
                    data = it
                }
                OutlinedButton(onClick = {
                    val newStatistic = statistic.copy(
                        name = name,
                        data = data,
                    )
                    onConfirm(newStatistic)
                }) {
                    Text("Update")
                }
            }
        }
    }
}