package at.orchaldir.ofws.routes.rpg

import at.orchaldir.ofws.model.rpg.statistic.STATISTIC_TYPE
import at.orchaldir.ofws.model.rpg.statistic.Statistic
import at.orchaldir.ofws.model.rpg.statistic.StatisticId
import io.ktor.resources.*

@Resource("/$STATISTIC_TYPE")
class StatisticRoutes {
    @Resource("create")
    class Create(val parent: StatisticRoutes = StatisticRoutes())

    @Resource("delete")
    class Delete(val id: StatisticId, val parent: StatisticRoutes = StatisticRoutes())

    @Resource("all")
    class GetAll(val parent: StatisticRoutes = StatisticRoutes())

    @Resource("get")
    class Get(val id: StatisticId, val parent: StatisticRoutes = StatisticRoutes())
}
