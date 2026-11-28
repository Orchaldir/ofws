package at.orchaldir.ofws

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.PermanentDrawerSheet
import androidx.compose.material3.PermanentNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.entryProvider
import kotlinx.serialization.Serializable
import androidx.navigation3.ui.NavDisplay
import at.orchaldir.ofws.model.rpg.statistic.StatisticId
import at.orchaldir.ofws.network.createHttpClient
import at.orchaldir.ofws.ui.UserScreen
import at.orchaldir.ofws.ui.rpg.StatisticEditor
import at.orchaldir.ofws.ui.rpg.StatisticScreen
import at.orchaldir.ofws.utils.SpacedColumn

@Serializable
sealed interface NavRoute

@Serializable
sealed interface StatisticNavRoute: NavRoute {
    @Serializable
    data object All: StatisticNavRoute
    @Serializable
    data class Edit(val id: StatisticId): StatisticNavRoute
}

@Serializable
private data object UserNavRoute: NavRoute

@Composable
fun BasicNavigation() {
    val backStack = remember { mutableStateListOf<NavRoute>(UserNavRoute) }
    val httpClient = createHttpClient()
    val navRoutes = listOf(
        StatisticNavRoute.All,
        UserNavRoute
    )

    PermanentNavigationDrawer(
        drawerContent = {
            PermanentDrawerSheet {
                SpacedColumn {
                    Spacer(Modifier.height(12.dp))
                    navRoutes.forEach { navRoute ->
                        NavigationDrawerItem(
                            label = { Text(navRoute.toString())},
                            selected = backStack == navRoute,
                            onClick = {
                                backStack.add(navRoute)
                            },
                            modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding),
                        )
                    }
                }
            }
        },
        content = {
            NavDisplay(
                backStack = backStack,
                onBack = { backStack.removeLastOrNull() },
                entryProvider = entryProvider {
                    entry<StatisticNavRoute.All> {
                        StatisticScreen(
                            httpClient,
                            { backStack.add(StatisticNavRoute.Edit(it)) }
                        )
                    }
                    entry<StatisticNavRoute.Edit> { edit ->
                        StatisticEditor(
                            httpClient,
                            edit.id,
                            {  backStack.add(StatisticNavRoute.All) },
                        )
                    }
                    entry<UserNavRoute> {
                        UserScreen(httpClient)
                    }
                },
            )
        }
    )
}