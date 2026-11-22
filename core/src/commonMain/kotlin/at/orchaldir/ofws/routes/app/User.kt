package at.orchaldir.ofws.routes.app

import at.orchaldir.ofws.model.app.USER_TYPE
import at.orchaldir.ofws.model.app.UserId
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
    class Update(val id: UserId, val parent: UserRoutes = UserRoutes())
}
