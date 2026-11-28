package at.orchaldir.ofws.network

import at.orchaldir.ofws.model.rpg.statistic.Statistic
import at.orchaldir.ofws.model.rpg.statistic.StatisticId
import at.orchaldir.ofws.routes.rpg.StatisticRoutes
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class StatisticClient(private val httpClient: HttpClient) {

    suspend fun createStatistic(): StatisticId =
        httpClient.get(StatisticRoutes.Create()).body()

    suspend fun deleteStatistic(statistic: Statistic) = deleteStatistic(statistic.id)

    suspend fun deleteStatistic(id: StatisticId) {
        httpClient.get(StatisticRoutes.Delete(id))
    }

    suspend fun getAllStatistics(): List<Statistic> =
        httpClient.get(StatisticRoutes.GetAll()).body()

    suspend fun updateStatistic(statistic: Statistic) {
        httpClient.post(StatisticRoutes()) {
            contentType(ContentType.Application.Json)
            setBody(statistic)
        }
    }
}