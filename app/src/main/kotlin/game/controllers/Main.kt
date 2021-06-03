package game

import javafx.beans.property.ReadOnlyListWrapper
import javafx.beans.value.ObservableBooleanValue
import javafx.beans.value.ObservableIntegerValue
import tornadofx.*

class ResourcesController: Controller() {
    override val scope = super.scope as GameScope

    val resourcesWithCapacities = ReadOnlyListWrapper<ResourceWithCapacity>()

    init {
        resourcesWithCapacities.bind(scope.gameProperty.objectBinding {
            it!!.mainSettlement.resourceListWithCapacities.asObservable()
        })
    }

    fun productionOf(type: ResourceType): ObservableIntegerValue {
        return scope.gameProperty.integerBinding {
            it!!.mainSettlement.getProductionOf(type)
        }
    }
}

data class BuildingWithCount(val type: BuildingType, val count: Int)

class BuildingsController: Controller() {
    override val scope = super.scope as GameScope

    val buildings = ReadOnlyListWrapper<BuildingWithCount>()

    init {
        buildings.bind(scope.gameProperty.objectBinding {
            it!!.mainSettlement.buildings
                .map { BuildingWithCount(it.key, it.value) }
                .asObservable()
        })
    }

    fun build(type: BuildingType) {
        scope.updateGame { game ->
            Game(listOf(game.mainSettlement.build(type)))
        }
    }

    fun canBuild(type: BuildingType): ObservableBooleanValue {
        return scope.gameProperty.booleanBinding {
            it!!.mainSettlement.canBuild(type)
        }
    }
}