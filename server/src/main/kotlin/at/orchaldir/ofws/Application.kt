package at.orchaldir.ofws

import at.orchaldir.ofws.action.REDUCER
import at.orchaldir.ofws.model.State
import at.orchaldir.ofws.model.rpg.statistic.Attribute
import at.orchaldir.ofws.model.rpg.statistic.DerivedAttribute
import at.orchaldir.ofws.model.rpg.statistic.DivisionOfValues
import at.orchaldir.ofws.model.rpg.statistic.FixedNumber
import at.orchaldir.ofws.model.rpg.statistic.ProductOfValues
import at.orchaldir.ofws.model.rpg.statistic.Skill
import at.orchaldir.ofws.model.rpg.statistic.Statistic
import at.orchaldir.ofws.model.rpg.statistic.StatisticId
import at.orchaldir.ofws.model.rpg.statistic.SumOfValues
import at.orchaldir.ofws.model.user.User
import at.orchaldir.ofws.model.user.UserId
import at.orchaldir.ofws.model.user.UserRole
import at.orchaldir.ofws.model.utils.name.Name
import at.orchaldir.ofws.routes.configureUserRouting
import at.orchaldir.ofws.routes.rpg.configureStatisticRouting
import at.orchaldir.ofws.utils.Storage
import at.orchaldir.ofws.utils.redux.DefaultStore
import at.orchaldir.ofws.utils.redux.middleware.LogAction
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.server.plugins.contentnegotiation.*
import io.ktor.server.plugins.cors.routing.*
import io.ktor.server.resources.*
import io.ktor.util.Attributes

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    install()

    val origin = "app"
    val admin = User(UserId(origin, 0), Name.init("Admin"), UserRole.Admin)
    val statistics = listOf(
        Statistic(StatisticId(origin, 0)),
        Statistic(StatisticId(origin, 1), data = DerivedAttribute(DivisionOfValues(FixedNumber(10), FixedNumber(3)))),
        Statistic(StatisticId(origin, 2), data = Skill(SumOfValues(listOf(FixedNumber(10), FixedNumber(3))))),
        Statistic(StatisticId(origin, 3), data = Skill(ProductOfValues(listOf(FixedNumber(10), FixedNumber(3))))),
    )
    val storages = listOf(
        Storage(admin),
        Storage(statistics),
    )
    val state = State(storages, origin)
    val store = DefaultStore(state, REDUCER, listOf(LogAction()))

    configureStatisticRouting(store)
    configureUserRouting(store)
}

private fun Application.install() {
    install(ContentNegotiation) {
        json()
    }
    install(CORS) {
        allowHeader(HttpHeaders.ContentType)
        allowMethod(HttpMethod.Delete)
        anyHost()
    }
    install(Resources)
}