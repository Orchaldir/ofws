package at.orchaldir.ofws.network

import at.orchaldir.ofws.routes.UserRoutes
import at.orchaldir.ofws.model.User
import at.orchaldir.ofws.model.UserId
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get

class UserApi(private val httpClient: HttpClient) {

    suspend fun createUser(): UserId =
        httpClient.get(UserRoutes.Create()).body()

    suspend fun deleteUser(user: User) = deleteUser(user.id)

    suspend fun deleteUser(id: UserId) {
        httpClient.get(UserRoutes.Delete(id))
    }

    suspend fun getAllUsers(): List<User> =
        httpClient.get(UserRoutes.GetAll()).body()

    suspend fun updateUser(user: User) {
        httpClient.get(UserRoutes.Update(user))
    }
}