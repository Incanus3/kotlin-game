package game

class Settlement(
    resources: Map<ResourceType, Int>? = null,
    buildings: Map<BuildingType, Int>? = null,
) {
    val capacities = ResourceType.values().associateWith { 1000 }
    val resources  = resources ?: ResourceType.values().associateWith { 0 }
    val buildings  = buildings ?: mapOf(
        BuildingType.FARM       to 1,
        BuildingType.WOODCUTTER to 1,
        BuildingType.QUARRY     to 1,
        BuildingType.MINE       to 1,
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

    fun getBuiltCount(type: BuildingType): Int {
        return buildings.getOrDefault(type, 0)
    }

    fun addResource(type: ResourceType, amount: Int): Settlement {
        val currentAmount = getResourceAmount(type)
        val capacity      = getResourceCapacity(type)

        return Settlement(
            resources + (type to minOf(currentAmount + amount, capacity)), buildings
        )
    }

    fun build(type: BuildingType): Settlement {
        val building        = Buildings.forType(type)!!
        val resourceUpdates = building.cost.associate { it.type to it.amount }

        return Settlement(
            ResourceType.values().associateWith {
                getResourceAmount(it) - resourceUpdates.getOrDefault(it, 0)
            },
            buildings + mapOf(type to getBuiltCount(type) + 1)
        )
    }

    fun canBuild(type: BuildingType): Boolean {
        val building = Buildings.forType(type)!!

        return building.cost.all { getResourceAmount(it.type) >= it.amount }
    }
}