package at.orchaldir.ofws

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform