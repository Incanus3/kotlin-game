package game.models

class Settlement(
    val resources:  Resources = Resources(),
    val capacities: Resources = Resources { all = 1000 },
    buildings: Map<BuildingType, Int> = emptyMap(),
) {
    val buildings = BuildingType.values().associateWith { 0 } + buildings

    private fun getBuildingLevel(type: BuildingType): Int {
        return buildings.getOrDefault(type, 0)
    }

    private fun getProduction(): Resources {
        return buildings.map {
            Buildings.forType(it.key).productionFor(it.value)
        }.reduce(Resources::plus)
    }

    fun getProductionOf(type: ResourceType): Int {
        return getProduction().forType(type)
    }

    fun produce(): Settlement {
        return Settlement((resources + getProduction()).cap(capacities), capacities, buildings)
    }

    fun build(type: BuildingType): Settlement {
        val level = getBuildingLevel(type) + 1

        return Settlement(
            resources - Buildings.forType(type).costFor(level), capacities,
            buildings + mapOf(type to level)
        )
    }

    fun canBuild(type: BuildingType): Boolean {
        val level = getBuildingLevel(type) + 1

        return resources.cover(Buildings.forType(type).costFor(level))
    }
}