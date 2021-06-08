package game.models

import kotlin.math.pow

enum class BuildingType {
    FARM, WOODCUTTER, QUARRY, MINE;

    override fun toString(): String {
        return name.lowercase().replaceFirstChar { it.uppercase() }
    }
}

abstract class Building {
    abstract val type:     BuildingType
    abstract val baseCost: Resources

    open val baseProduction:  Resources = Resources()
    open val baseConsumption: Resources = Resources()

    fun costFor(level: Int): Resources {
        return baseCost * level
    }

    fun productionFor(level: Int): Resources {
        return baseProduction * 2.toFloat().pow(level - 1).toInt()
    }

    fun consumptionFor(level: Int): Resources {
        return baseConsumption * 2.toFloat().pow(level - 1).toInt()
    }
}

class Farm: Building() {
    override val type           = BuildingType.FARM
    override val baseCost       = Resources { timber = 100 }
    override val baseProduction = Resources { food   = 10  }
}

class Woodcutter: Building() {
    override val type           = BuildingType.WOODCUTTER
    override val baseCost       = Resources { stone  = 100 }
    override val baseProduction = Resources { timber = 10  }
}

class Quarry: Building() {
    override val type           = BuildingType.QUARRY
    override val baseCost       = Resources { timber = 100 }
    override val baseProduction = Resources { stone  = 10  }
}

class Mine: Building() {
    override val type           = BuildingType.MINE
    override val baseCost       = Resources { timber = 100; stone = 100 }
    override val baseProduction = Resources { iron   = 10               }
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