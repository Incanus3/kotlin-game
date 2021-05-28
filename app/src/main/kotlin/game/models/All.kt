package game

enum class ResourceType {
    TIMBER, STONE, IRON
}

data class Resource(val type: ResourceType, val amount: Int)

class Settlement {
    val resources: Map<ResourceType, Int>

    val resourceList: List<Resource>
        get() = resources.map { Resource(it.key, it.value) }

    constructor(resources: Map<ResourceType, Int>? = null) {
        this.resources = resources ?: mapOf(
            ResourceType.TIMBER to 0,
            ResourceType.STONE  to 0,
            ResourceType.IRON   to 0
        )
    }

    fun getResourceAmount(type: ResourceType): Int {
        return resources.getOrDefault(type, 0)
    }

    fun addResource(type: ResourceType, amount: Int): Settlement {
        return Settlement(resources + (type to getResourceAmount(type) + amount))
    }
}
