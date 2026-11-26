package at.orchaldir.ofws

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import at.orchaldir.ofws.network.createHttpClient
import at.orchaldir.ofws.ui.UserScreen
import at.orchaldir.ofws.ui.rpg.StatisticScreen

@Composable
fun App() {
    val httpClient = createHttpClient()

    MaterialTheme {
        UserScreen(httpClient)
    }
}
