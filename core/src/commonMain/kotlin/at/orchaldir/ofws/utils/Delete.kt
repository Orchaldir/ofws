package at.orchaldir.ofws.utils

data class CannotDeleteException(
    val result: DeleteResult,
) : Exception("Cannot delete ${result.id.print()} because of ${result.countElements()} elements!")

data class DeleteResult(
    val id: Id,
    val elements: MutableMap<String, MutableSet<Id>> = mutableMapOf(),
) {
    fun <ID : Id> addIds(ids: Collection<ID>): DeleteResult {
        val first = ids.firstOrNull() ?: return this

        elements.getOrPut(first.type()) {
            mutableSetOf<ID>() as MutableSet<Id>
        }.addAll(ids)

        return this
    }

    fun <ID : Id> addId(id: ID) = addIds(setOf(id))

    fun <ID : Id, ELEMENT : Element<ID>> addElements(elements: Collection<ELEMENT>) =
        addIds(elements.map { it.id() })

    fun <ID : Id, ELEMENT : Element<ID>> addElement(element: ELEMENT?): DeleteResult {
        if (element != null) {
            addId(element.id())
        }

        return this
    }

    fun apply(function: (DeleteResult) -> Unit): DeleteResult {
        function.invoke(this)

        return this
    }

    fun countElements(): Int {
        var count = 0

        elements.forEach { (_, ids) ->
            count += ids.size
        }

        return count
    }

    fun validate() {
        if (!canDelete()) {
            throw CannotDeleteException(this)
        }
    }

    fun canDelete() = elements.isEmpty()
}