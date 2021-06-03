package game.controllers

import game.GameScope
import game.models.ResourceType
import game.models.ResourceWithCapacity
import javafx.beans.property.ReadOnlyListWrapper
import javafx.beans.value.ObservableIntegerValue
import tornadofx.Controller
import tornadofx.asObservable
import tornadofx.integerBinding
import tornadofx.objectBinding

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