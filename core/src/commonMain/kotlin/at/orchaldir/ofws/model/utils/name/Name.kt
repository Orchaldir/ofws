package at.orchaldir.ofws.model.utils.name

import at.orchaldir.ofws.utils.Id
import at.orchaldir.ofws.utils.titlecaseFirstChar
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
