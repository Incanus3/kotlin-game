package game.controllers

import game.GameScope
import game.models.ResourceType
import javafx.beans.property.ReadOnlyIntegerWrapper
import javafx.beans.property.ReadOnlyListWrapper
import tornadofx.*

class ResourceVM(
    val type: ResourceType, val amount: Int, val capacity: Int, controller: ResourcesController
) {
    val filled = amount / capacity.toFloat()

    val productionProperty = ReadOnlyIntegerWrapper()
    val production by productionProperty

    init {
        productionProperty.bind(controller.gameProperty.integerBinding {
            it!!.mainSettlement.getProductionOf(type)
        })
    }
}

class ResourcesController: Controller() {
    override val scope = super.scope as GameScope

    val gameProperty            = scope.gameProperty
    val resourcesWithCapacities = ReadOnlyListWrapper<ResourceVM>()

    init {
        resourcesWithCapacities.bind(gameProperty.objectBinding {
            val settlement = it!!.mainSettlement

            settlement.resources
                .map {
                    ResourceVM(
                        it.key, it.value, settlement.getResourceCapacity(it.key), this
                    )
                }
                .asObservable()
        })
    }
}