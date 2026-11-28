package at.orchaldir.ofws.model.user

import at.orchaldir.ofws.model.State
import at.orchaldir.ofws.model.utils.name.Name
import at.orchaldir.ofws.utils.DeleteResult
import at.orchaldir.ofws.utils.Element
import at.orchaldir.ofws.utils.Id
import kotlinx.serialization.Serializable

const val USER_TYPE = "User"

@Serializable
data class UserId(
    val origin: String,
    val index: Int,
) : Id {

    override fun type() = USER_TYPE
    override fun origin() = origin
    override fun index() = index

}

@Serializable
data class User(
    val id: UserId,
    val name: Name = Name.init(id),
    val role: UserRole = UserRole.Player,
) : Element<UserId> {

    override fun id() = id
    override fun name() = name

    override fun canDelete(state: State) = DeleteResult(id)

}