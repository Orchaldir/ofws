package at.orchaldir.ofws.model.app

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
    val name: String,
    val role: UserRole,
) : Element<UserId> {

    override fun id() = id

}