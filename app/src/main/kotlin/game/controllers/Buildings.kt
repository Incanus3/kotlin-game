package game.controllers

import game.GameScope
import game.models.BuildingType
import game.models.Buildings
import javafx.beans.property.ReadOnlyBooleanWrapper
import javafx.beans.property.ReadOnlyListWrapper
import tornadofx.*

class BuildingVM(val type: BuildingType, val level: Int, controller: BuildingsController) {
    private val building = Buildings.forType(type)

    val itself: BuildingVM
        get() = this
    val currentProduction: String
        get() = building.productionFor(level).toString()
    val currentConsumption: String
        get() = building.consumptionFor(level).toString()
    val nextLvlProduction: String
        get() = building.productionFor(level + 1).toString()
    val nextLvlConsumption: String
        get() = building.consumptionFor(level + 1).toString()
    val nextLvlCost: String
        get() = building.costFor(level + 1).toString()

    val canBeBuiltProperty = ReadOnlyBooleanWrapper().apply {
        bind(controller.gameProperty.booleanBinding {
            it!!.mainSettlement.canBuild(type)
        })
    }
    val canBeBuilt by canBeBuiltProperty
}

class BuildingsController: Controller() {
    override val scope = super.scope as GameScope

    val gameProperty = scope.gameProperty
    val buildings    = ReadOnlyListWrapper<BuildingVM>().also {
        it.bind(gameProperty.objectBinding {
            it!!.mainSettlement.buildings
                .map { BuildingVM(it.key, it.value, this) }
                .asObservable()
        })
    }

    fun build(type: BuildingType) {
        scope.updateGame {
            it.withSettlements(listOf(it.mainSettlement.build(type)))
        }
    }
}