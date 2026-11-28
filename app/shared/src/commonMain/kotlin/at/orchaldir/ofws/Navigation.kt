package at.orchaldir.ofws

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.navigation3.runtime.entryProvider
import kotlinx.serialization.Serializable
import androidx.navigation3.ui.NavDisplay
import at.orchaldir.ofws.network.createHttpClient
import at.orchaldir.ofws.ui.UserScreen
import at.orchaldir.ofws.ui.rpg.StatisticScreen

@Serializable
sealed interface NavRoute

@Serializable
private data object StatisticNavRoute: NavRoute

@Serializable
private data object UserNavRoute: NavRoute

@Composable
fun BasicNavigation() {
    val backStack = remember { mutableStateListOf<NavRoute>(UserNavRoute) }
    val httpClient = createHttpClient()

    NavDisplay(
        backStack = backStack,
        onBack = { backStack.removeLastOrNull() },
        entryProvider = entryProvider {
            entry<StatisticNavRoute> {
                StatisticScreen(httpClient)
            }
            entry<UserNavRoute> {
                UserScreen(httpClient)
            }
        },
    )
}