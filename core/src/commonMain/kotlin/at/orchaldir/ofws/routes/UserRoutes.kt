package at.orchaldir.ofws.routes

import at.orchaldir.ofws.model.user.USER_TYPE
import at.orchaldir.ofws.model.user.User
import at.orchaldir.ofws.model.user.UserId
import io.ktor.resources.*

@Resource("/$USER_TYPE")
class UserRoutes {

    @Resource("create")
    class Create(val parent: UserRoutes = UserRoutes())

    @Resource("delete")
    class Delete(val id: UserId, val parent: UserRoutes = UserRoutes())

    @Resource("all")
    class GetAll(val parent: UserRoutes = UserRoutes())

    @Resource("get")
    class Get(val id: UserId, val parent: UserRoutes = UserRoutes())

}
