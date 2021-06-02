package game

import javafx.beans.property.ReadOnlyListWrapper
import tornadofx.*

class ResourcesController: Controller() {
    override val scope = super.scope as GameScope

    val resourcesWithCapacities = ReadOnlyListWrapper<ResourceWithCapacity>()

    init {
        resourcesWithCapacities.bind(scope.gameProperty.objectBinding {
            scope.gameProperty.value.mainSettlement.resourceListWithCapacities.asObservable()
        })
    }
}

data class BuildingWithCount(val type: BuildingType, val count: Int)

class BuildingsController: Controller() {
    override val scope = super.scope as GameScope

    val buildings = ReadOnlyListWrapper<BuildingWithCount>()

    init {
        buildings.bind(scope.gameProperty.objectBinding {
            scope.gameProperty.value.mainSettlement.buildings
                .map { BuildingWithCount(it.key, it.value) }
                .asObservable()
        })
    }
}