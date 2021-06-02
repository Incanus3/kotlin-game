package game

import javafx.beans.property.ReadOnlyListWrapper
import tornadofx.*

class ResourcesController: Controller() {
    override val scope = super.scope as GameScope

    val resourceListWithCapacitiesProperty = ReadOnlyListWrapper<ResourceWithCapacity>()

    init {
        resourceListWithCapacitiesProperty.bind(scope.gameProperty.objectBinding {
            scope.gameProperty.value.mainSettlement.resourceListWithCapacities.asObservable()
        })
    }
}

class BuildingsController: Controller() {

}