package at.orchaldir.ofws.model.app

import at.orchaldir.ofws.utils.Id
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

const val USER_TYPE = "User"

@JvmInline
@Serializable
value class UserId(val value: String) : Id {

    override fun type() = USER_TYPE
    override fun value() = value

}

@Serializable
data class User(
    val name: String,
    val role: UserRole,
)