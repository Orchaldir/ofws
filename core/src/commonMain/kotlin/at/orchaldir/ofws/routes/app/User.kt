package at.orchaldir.ofws.routes.app

import at.orchaldir.ofws.model.app.USER_TYPE
import io.ktor.resources.Resource

@Resource("/$USER_TYPE")
class UserRoutes {
    @Resource("all")
    class All(val parent: UserRoutes = UserRoutes())
}
