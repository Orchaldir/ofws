package at.orchaldir.ofws.model

import at.orchaldir.ofws.utils.Element
import at.orchaldir.ofws.utils.Id
import at.orchaldir.ofws.utils.Storage

val ELEMENTS =
    setOf(
        USER_TYPE
    )

data class State(
    val modName: String,
    val storageMap: Map<String, Storage<*, *>> = emptyMap(),
) {
    constructor(
        modName: String,
        storage: Storage<*, *>,
    ) : this(modName, mapOf(storage.getType() to storage))

    constructor(
        modName: String,
        storageList: List<Storage<*, *>>,
    ) : this(modName, storageList.associateBy { it.getType() })

    fun getUserStorage() = getStorage<UserId, User>(USER_TYPE)

    fun <ID : Id, ELEMENT : Element<ID>> getStorage(id: ID) = getStorage<ID, ELEMENT>(id.type())

    private fun <ID : Id, ELEMENT : Element<ID>> getStorage(type: String): Storage<ID, ELEMENT> {
        val storage = storageMap[type]

        if (storage != null) {
            @Suppress("UNCHECKED_CAST")
            return storage as Storage<ID, ELEMENT>
        }

        @Suppress("UNCHECKED_CAST")
        return createStorage(type) as Storage<ID, ELEMENT>
    }

    fun <ID : Id> removeStorage(id: ID) = removeStorage(id.type())

    fun removeStorage(type: String): State {
        val newMap = storageMap.toMutableMap()
        newMap.remove(type)

        return copy(storageMap = newMap)
    }

    fun <ID : Id, ELEMENT : Element<ID>> updateStorage(element: ELEMENT) = updateStorage(Storage(element))
    fun <ID : Id, ELEMENT : Element<ID>> updateStorage(elements: List<ELEMENT>) = updateStorage(Storage(elements))

    fun updateStorage(storage: Storage<*, *>): State {
        val newMap = storageMap.toMutableMap()
        newMap[storage.getType()] = storage

        return copy(storageMap = newMap)
    }

    fun updateStorage(storages: Collection<Storage<*, *>>): State {
        val newMap = storageMap.toMutableMap()

        storages.forEach {
            newMap[it.getType()] = it
        }

        return copy(storageMap = newMap)
    }
}

fun createStorage(type: String) = when (type) {
    USER_TYPE -> Storage<UserId, User>(type)
    else -> throw IllegalArgumentException("Unknown type $type")
}
