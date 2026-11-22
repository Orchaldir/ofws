package at.orchaldir.ofws.utils

interface Id {
    fun type(): String
    fun plural() = type() + "s"
    fun value(): String
    fun print() = "${type()} ${value()}"
}

interface Element<ID> {
    fun id(): ID
}

data class Storage<ID : Id, ELEMENT : Element<ID>>(
    private val elements: Map<ID, ELEMENT>,
    private val type: String,
) {
    constructor(element: ELEMENT) : this(listOf(element), element.id().type())

    constructor(elements: List<ELEMENT>) : this(
        elements,
        elements.first().id().type(),
    )

    constructor(elements: List<ELEMENT>, type: String) : this(
        elements.associateBy { it.id() },
        type,
    )

    fun add(element: ELEMENT): Storage<ID, ELEMENT> {
        return Storage(elements + mapOf(element.id() to element), type)
    }

    fun remove(id: ID) = copy(elements = elements - setOf(id))

    fun update(element: ELEMENT) = copy(elements = elements + mapOf(element.id() to element))

    fun update(updated: List<ELEMENT>) = copy(elements = elements + updated.associateBy { it.id() })

    // getters

    fun getIds() = elements.keys

    fun getAll() = elements.values

    fun getAllExcept(id: ID?) = elements.values
        .filter { it.id() != id }

    fun getSize() = elements.size

    fun getType() = type

    fun get(id: ID) = elements[id]

    fun get(ids: Collection<ID>) = ids.mapNotNull { elements[it] }

    fun getOptional(id: ID?) = if (id != null) {
        elements[id]
    } else {
        null
    }

    fun getOrThrow(id: ID, message: () -> String) =
        elements[id] ?: throw IllegalArgumentException(message())

    fun getOrThrow(id: ID) = getOrThrow(id) { "Requires unknown ${id.print()}!" }

    fun getOrThrow(ids: Collection<ID>) = ids.map { getOrThrow(it) }

    fun contains(id: ID) = elements.containsKey(id)

    fun require(id: ID) {
        require(contains(id)) { "Requires unknown ${id.print()}!" }
    }

    fun requireOptional(id: ID?) {
        if (id != null) {
            require(id)
        }
    }

    fun requireOptional(id: ID?, message: () -> String) {
        if (id != null) {
            require(id, message)
        }
    }

    fun require(ids: Collection<ID>) = ids.forEach { require(it) }

    fun requireOptional(ids: Collection<ID?>) = ids.forEach { requireOptional(it) }

    fun require(id: ID, message: () -> String) {
        require(contains(id)) { message() }
    }
}
