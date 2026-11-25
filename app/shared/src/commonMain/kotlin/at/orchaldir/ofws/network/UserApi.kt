package at.orchaldir.ofws.network

import at.orchaldir.ofws.model.user.User
import at.orchaldir.ofws.model.user.UserId
import at.orchaldir.ofws.routes.UserRoutes
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.resources.*
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

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
        httpClient.post(UserRoutes()) {
            contentType(ContentType.Application.Json)
            setBody(user)
        }
    }
}