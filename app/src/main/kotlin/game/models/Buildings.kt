package game.models

enum class BuildingType {
    FARM, WOODCUTTER, QUARRY, MINE;

    override fun toString(): String {
        return name.lowercase().replaceFirstChar { it.uppercase() }
    }
}

abstract class Building {
    abstract val type: BuildingType
    abstract val cost: Resources

    open val production:  Resources = Resources()
    open val consumption: Resources = Resources()
}

class Farm: Building() {
    override val type       = BuildingType.FARM
    override val cost       = Resources { timber = 100 }
    override val production = Resources { food   = 10  }
}

class Woodcutter: Building() {
    override val type       = BuildingType.WOODCUTTER
    override val cost       = Resources { stone  = 100 }
    override val production = Resources { timber = 10  }
}

class Quarry: Building() {
    override val type       = BuildingType.QUARRY
    override val cost       = Resources { timber = 100 }
    override val production = Resources { stone  = 10  }
}

class Mine: Building() {
    override val type       = BuildingType.MINE
    override val cost       = Resources { timber = 100; stone = 100 }
    override val production = Resources { iron   = 10               }
}

class Buildings {
    companion object {
        private val buildings = mapOf(
            BuildingType.FARM       to Farm(),
            BuildingType.WOODCUTTER to Woodcutter(),
            BuildingType.QUARRY     to Quarry(),
            BuildingType.MINE       to Mine(),
        )

        fun forType(type: BuildingType) = buildings[type]!!
    }
}