package at.orchaldir.ofws.utils

fun String.titlecaseFirstChar() = replaceFirstChar(Char::titlecase)

fun <T : Enum<T>> T.splitName(): List<String> {
    val parts = mutableListOf<String>()
    var current = ""
    var wasUpperCase = false

    name.forEach { char ->
        val isUpperCase = char.isUpperCase()

        if (isUpperCase && !wasUpperCase) {
            if (current.isNotEmpty()) {
                parts.add(current)
                current = ""
            }
        }

        current += char
        wasUpperCase = isUpperCase
    }

    if (current.isNotEmpty()) {
        parts.add(current)
    }

    return parts
}

fun <T : Enum<T>> T.nameWithSpaces() = splitName().joinToString(" ")