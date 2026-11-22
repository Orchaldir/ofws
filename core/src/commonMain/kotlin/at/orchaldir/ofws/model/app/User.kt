package at.orchaldir.ofws.model.app

import kotlinx.serialization.Serializable

@Serializable
data class User(
    val name: String,
    val role: UserRole,
)