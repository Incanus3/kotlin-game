package game.models

class Settlement(
    val resources: Resources = Resources(),
    buildings: Map<BuildingType, Int>? = null,
) {
    val capacities = Resources { all = 1000; food = 500 }
    val buildings  = buildings ?: mapOf(
        BuildingType.FARM       to 1,
        BuildingType.WOODCUTTER to 1,
        BuildingType.QUARRY     to 1,
        BuildingType.MINE       to 1,
    )

    private fun getBuiltCount(type: BuildingType): Int {
        return buildings.getOrDefault(type, 0)
    }

    private fun getProduction(): Resources {
        return buildings.map {
            Buildings.forType(it.key).production * it.value
        }.reduce(Resources::plus)
    }

    // FIXME: this is very ineffective
    fun getProductionOf(type: ResourceType): Int {
        return getProduction().forType(type)
    }

    fun produce(): Settlement {
        return Settlement((resources + getProduction()).cap(capacities), buildings)
    }

    fun build(type: BuildingType): Settlement {
        return Settlement(
            resources - Buildings.forType(type).cost,
            buildings + mapOf(type to getBuiltCount(type) + 1)
        )
    }

    fun canBuild(type: BuildingType): Boolean {
        return resources.cover(Buildings.forType(type).cost)
    }
}