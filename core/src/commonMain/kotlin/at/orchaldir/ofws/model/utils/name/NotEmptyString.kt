package at.orchaldir.ofws.model.utils.name

import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
value class NotEmptyString private constructor(val text: String) {

    companion object {
        fun init(name: String) = NotEmptyString(name.trim())
    }

    init {
        require(text.isNotEmpty()) { "NotEmptyString is empty!" }
    }

}
