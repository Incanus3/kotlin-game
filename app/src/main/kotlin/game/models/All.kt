package game

enum class ResourceType {
    FOOD, TIMBER, STONE, IRON;

    override fun toString(): String {
        return name.lowercase().replaceFirstChar { it.uppercase() }
    }
}

data class Resource(val type: ResourceType, val amount: Int)
data class ResourceWithCapacity(val type: ResourceType, val amount: Int, val capacity: Int)

class Settlement(resources: Map<ResourceType, Int>? = null, capacities: Map<ResourceType, Int>? = null) {
    val resources:  Map<ResourceType, Int> = resources ?: mapOf(
        ResourceType.FOOD   to 0,
        ResourceType.TIMBER to 0,
        ResourceType.STONE  to 0,
        ResourceType.IRON   to 0
    )
    val capacities: Map<ResourceType, Int> = capacities ?: mapOf(
        ResourceType.FOOD   to 1000,
        ResourceType.TIMBER to 1000,
        ResourceType.STONE  to 1000,
        ResourceType.IRON   to 1000,
    )

    val resourceList: List<Resource>
        get() = resources.map { Resource(it.key, it.value) }
    val resourceListWithCapacities: List<ResourceWithCapacity>
        get() = resources.map {
            ResourceWithCapacity(it.key, it.value, getResourceCapacity(it.key))
        }

    fun getResourceAmount(type: ResourceType): Int {
        return resources.getOrDefault(type, 0)
    }

    fun getResourceCapacity(type: ResourceType): Int {
        return capacities.getOrDefault(type, 0)
    }

    fun addResource(type: ResourceType, amount: Int): Settlement {
        val currentAmount = getResourceAmount(type)
        val capacity      = getResourceCapacity(type)

        return Settlement(resources + (type to minOf(currentAmount + amount, capacity)), capacities)
    }
}
