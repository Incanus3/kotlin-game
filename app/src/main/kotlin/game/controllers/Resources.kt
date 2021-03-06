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

    val productionProperty = ReadOnlyIntegerWrapper().apply {
        bind(controller.gameProperty.integerBinding {
            it!!.mainSettlement.getProductionOf(type)
        })
    }
    val production by productionProperty
}

class ResourcesController: Controller() {
    override val scope = super.scope as GameScope

    val gameProperty            = scope.gameProperty
    val resourcesWithCapacities = ReadOnlyListWrapper<ResourceVM>().also {
        it.bind(gameProperty.objectBinding {
            val settlement = it!!.mainSettlement

            settlement.resources
                .map {
                    ResourceVM(
                        it.type, it.amount, settlement.capacities.forType(it.type), this
                    )
                }
                .asObservable()
        })
    }
}