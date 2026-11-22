package at.orchaldir.ofws.network

import at.orchaldir.ofws.routes.app.UserRoutes
import at.orchaldir.ofws.model.app.User
import at.orchaldir.ofws.model.app.UserId
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get

class UserApi(private val httpClient: HttpClient) {

    suspend fun getAllUsers(): List<User> {
        return httpClient.get(UserRoutes.All()).body()
    }

    suspend fun deleteUser(user: User) = deleteUser(user.id)

    suspend fun deleteUser(id: UserId) {
        httpClient.get(UserRoutes.Delete(id))
    }
}