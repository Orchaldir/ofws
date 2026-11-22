package at.orchaldir.ofws.network

import at.orchaldir.ofws.model.app.USER_TYPE
import at.orchaldir.ofws.model.app.User
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.resources.get
import io.ktor.resources.Resource

@Resource("/$USER_TYPE")
class UserRoutes {
    @Resource("all")
    class All(
        val parent: UserRoutes = UserRoutes(),
    )
}

class UserApi(private val httpClient: HttpClient) {

    suspend fun getAllUsers(): List<User> {
        return httpClient.get(UserRoutes.All()).body()
    }
}