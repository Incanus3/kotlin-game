package game

enum class BuildingType {
    FARM, WOODCUTTER, QUARRY, MINE;

    override fun toString(): String {
        return name.lowercase().replaceFirstChar { it.uppercase() }
    }
}

abstract class Building {
    abstract val type: BuildingType
    abstract val cost: List<Resource>

    open val production:  List<Resource> = emptyList()
    open val consumption: List<Resource> = emptyList()
}

class Farm: Building() {
    override val type       = BuildingType.FARM
    override val cost       = listOf(Resource(ResourceType.TIMBER, 100))
    override val production = listOf(Resource(ResourceType.FOOD,   10 ))
}

class Woodcutter: Building() {
    override val type       = BuildingType.WOODCUTTER
    override val cost       = listOf(Resource(ResourceType.STONE,  100))
    override val production = listOf(Resource(ResourceType.TIMBER, 10 ))
}

class Quarry: Building() {
    override val type       = BuildingType.QUARRY
    override val cost       = listOf(Resource(ResourceType.TIMBER, 100))
    override val production = listOf(Resource(ResourceType.STONE,  10 ))
}

class Mine: Building() {
    override val type = BuildingType.MINE
    override val cost = listOf(
        Resource(ResourceType.TIMBER, 100),
        Resource(ResourceType.STONE,  100),
    )
    override val production = listOf(Resource(ResourceType.IRON, 10))
}

class Buildings {
    companion object {
        val buildings = mapOf(
            BuildingType.FARM       to Farm(),
            BuildingType.WOODCUTTER to Woodcutter(),
            BuildingType.QUARRY     to Quarry(),
            BuildingType.MINE       to Mine(),
        )

        fun forType(type: BuildingType) = buildings[type]!!
    }
}