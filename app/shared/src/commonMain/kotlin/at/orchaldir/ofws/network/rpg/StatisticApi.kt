package at.orchaldir.ofws.network.rpg

import at.orchaldir.ofws.model.rpg.statistic.Statistic
import at.orchaldir.ofws.model.rpg.statistic.StatisticId
import at.orchaldir.ofws.routes.rpg.StatisticRoutes
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.resources.*

class StatisticApi(private val httpClient: HttpClient) {

    suspend fun createStatistic(): StatisticId =
        httpClient.get(StatisticRoutes.Create()).body()

    suspend fun deleteStatistic(user: Statistic) = deleteStatistic(user.id)

    suspend fun deleteStatistic(id: StatisticId) {
        httpClient.get(StatisticRoutes.Delete(id))
    }

    suspend fun getAllStatistics(): List<Statistic> =
        httpClient.get(StatisticRoutes.GetAll()).body()

    suspend fun updateStatistic(user: Statistic) {
        httpClient.get(StatisticRoutes.Update(user))
    }
}