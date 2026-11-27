package at.orchaldir.ofws.utils

fun String.titlecaseFirstChar() = replaceFirstChar(Char::titlecase)

fun <T : Enum<T>> T.splitName(): List<String> {
    val parts = mutableListOf<String>()
    var current = ""

    name.forEach { char ->
        if (char.isUpperCase()) {
            if (current.isNotEmpty()) {
                parts.add(current)
                current = ""
            }
        }

        current += char
    }

    if (current.isNotEmpty()) {
        parts.add(current)
    }

    return parts
}