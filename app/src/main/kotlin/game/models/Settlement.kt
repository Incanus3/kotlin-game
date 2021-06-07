package game.models

class Settlement(
    val resources:  Resources = Resources(),
    val capacities: Resources = Resources { all = 1000 },
    buildings: Map<BuildingType, Int> = emptyMap(),
) {
    val buildings = BuildingType.values().associateWith { 0 } + buildings

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
        return Settlement((resources + getProduction()).cap(capacities), capacities, buildings)
    }

    fun build(type: BuildingType): Settlement {
        return Settlement(
            resources - Buildings.forType(type).cost, capacities,
            buildings + mapOf(type to getBuiltCount(type) + 1)
        )
    }

    fun canBuild(type: BuildingType): Boolean {
        return resources.cover(Buildings.forType(type).cost)
    }
}