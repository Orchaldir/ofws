package at.orchaldir.ofws.model.utils

import at.orchaldir.ofws.utils.Id
import kotlinx.serialization.Serializable
import kotlin.jvm.JvmInline

@JvmInline
@Serializable
value class Name private constructor(val text: String) {

    companion object {
        fun init(name: String) = Name(name.trim().titlecaseFirstChar())
        fun <ID : Id> init(id: ID) = Name(id.print())
    }

    init {
        require(text.isNotEmpty()) { "Name is empty!" }
    }

}

fun String.titlecaseFirstChar() = replaceFirstChar(Char::titlecase)
