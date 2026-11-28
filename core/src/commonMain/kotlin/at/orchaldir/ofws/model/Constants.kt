package at.orchaldir.ofws.model

import at.orchaldir.ofws.model.rpg.statistic.StatisticId
import at.orchaldir.ofws.model.user.UserId

const val TEST_ORIGIN = "test"

val STATISTIC_ID_0 = StatisticId(TEST_ORIGIN, 0)
val STATISTIC_ID_UNKNOWN = StatisticId(TEST_ORIGIN, 99)
val USER_ID_0 = UserId(TEST_ORIGIN, 0)
val USER_ID_UNKNOWN = UserId(TEST_ORIGIN, 99)