package at.orchaldir.ofws.routes

import at.orchaldir.ofws.model.USER_TYPE
import at.orchaldir.ofws.model.User
import at.orchaldir.ofws.model.UserId
import io.ktor.resources.Resource

@Resource("/$USER_TYPE")
class UserRoutes {
    @Resource("all")
    class All(val parent: UserRoutes = UserRoutes())

    @Resource("details")
    class Details(val id: UserId, val parent: UserRoutes = UserRoutes())

    @Resource("new")
    class New(val parent: UserRoutes = UserRoutes())

    @Resource("delete")
    class Delete(val id: UserId, val parent: UserRoutes = UserRoutes())

    @Resource("update")
    class Update(val user: User, val parent: UserRoutes = UserRoutes())
}
