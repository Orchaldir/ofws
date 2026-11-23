package at.orchaldir.ofws.model

import at.orchaldir.ofws.model.user.UserId

const val TEST_ORIGIN = "test"

val USER_ID_0 = UserId(TEST_ORIGIN, 0)
val USER_ID_UNKNOWN = UserId(TEST_ORIGIN, 99)