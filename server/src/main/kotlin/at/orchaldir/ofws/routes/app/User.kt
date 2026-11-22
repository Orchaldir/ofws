package at.orchaldir.ofws.routes.app

import at.orchaldir.ofws.action.Action
import at.orchaldir.ofws.model.State
import at.orchaldir.ofws.model.app.USER_TYPE
import at.orchaldir.ofws.model.app.UserId
import at.orchaldir.ofws.utils.redux.Store
import io.ktor.resources.Resource
import io.ktor.server.application.Application
import io.ktor.server.resources.get
import io.ktor.server.response.respond
import io.ktor.server.routing.get
import io.ktor.server.routing.route
import io.ktor.server.routing.routing


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

fun Application.configureUserRouting(store: Store<Action, State>) {
    routing {
        route("/user") {
            get {
                val state = store.getState()

                call.respond(state.getUserStorage().getAll())
            }
        }
    }
}
