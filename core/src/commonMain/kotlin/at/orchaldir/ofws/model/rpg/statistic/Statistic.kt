package at.orchaldir.ofws.model.rpg.statistic

import at.orchaldir.ofws.model.State
import at.orchaldir.ofws.model.utils.Name
import at.orchaldir.ofws.utils.DeleteResult
import at.orchaldir.ofws.utils.Element
import at.orchaldir.ofws.utils.Id
import kotlinx.serialization.Serializable

const val STATISTIC_TYPE = "Statistic"

@Serializable
data class StatisticId(
    val origin: String,
    val index: Int,
) : Id {

    override fun type() = STATISTIC_TYPE
    override fun origin() = origin
    override fun index() = index

}

@Serializable
data class Statistic(
    val id: StatisticId,
    val name: Name = Name.init(id),
) : Element<StatisticId> {

    override fun id() = id

    override fun canDelete(state: State) = DeleteResult(id)

}