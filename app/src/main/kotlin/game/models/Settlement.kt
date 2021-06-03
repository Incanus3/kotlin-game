package game.models

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

    fun getResourceAmount(type: ResourceType): Int {
        return resources.getOrDefault(type, 0)
    }

    fun getResourceCapacity(type: ResourceType): Int {
        return capacities.getOrDefault(type, 0)
    }

    fun getBuiltCount(type: BuildingType): Int {
        return buildings.getOrDefault(type, 0)
    }

    fun getProduction(): Map<ResourceType, Int> {
        return buildings.flatMap { (type, count) ->
            Buildings.forType(type).production.map { Resource(it.type, it.amount * count ) }
        }
            .groupBy(Resource::type, Resource::amount)
            .mapValues { it.value.sum() }
    }

    // FIXME: this is very ineffective
    fun getProductionOf(type: ResourceType): Int {
        return getProduction()[type]!!
    }

    fun addResource(type: ResourceType, amount: Int): Settlement {
        val currentAmount = getResourceAmount(type)
        val capacity      = getResourceCapacity(type)

        return Settlement(
            resources + (type to minOf(currentAmount + amount, capacity)), buildings
        )
    }

    fun produce(): Settlement {
        return getProduction().asIterable().fold(this) { settlement, (resourceType, amount) ->
            settlement.addResource(resourceType, amount)
        }
    }

    fun build(type: BuildingType): Settlement {
        val resourceUpdates = Buildings.forType(type).cost.associate { it.type to it.amount }

        return Settlement(
            ResourceType.values().associateWith {
                getResourceAmount(it) - resourceUpdates.getOrDefault(it, 0)
            },
            buildings + mapOf(type to getBuiltCount(type) + 1)
        )
    }

    fun canBuild(type: BuildingType): Boolean {
        return Buildings.forType(type).cost.all { getResourceAmount(it.type) >= it.amount }
    }
}