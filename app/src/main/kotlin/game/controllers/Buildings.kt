package game.controllers

import game.models.BuildingType
import game.models.Buildings
import game.models.Game
import game.GameScope
import javafx.beans.property.ReadOnlyBooleanWrapper
import javafx.beans.property.ReadOnlyListWrapper
import tornadofx.Controller
import tornadofx.asObservable
import tornadofx.booleanBinding
import tornadofx.objectBinding

class BuildingVM(val type: BuildingType, val count: Int, controller: BuildingsController) {
    val building   = Buildings.forType(type)
    val canBeBuilt = ReadOnlyBooleanWrapper()

    val itself: BuildingVM
        get() = this
    val costString: String
        get() = building.cost.map { "${it.amount} ${it.type}" }.joinToString()
    val productionString: String
        get() = building.production.map { "${it.amount} ${it.type}" }.joinToString()
    val consumptionString: String
        get() = building.consumption.map { "${it.amount} ${it.type}" }.joinToString()

    init {
        canBeBuilt.bind(controller.gameProperty.booleanBinding {
            it!!.mainSettlement.canBuild(type)
        })
    }
}

class BuildingsController: Controller() {
    override val scope = super.scope as GameScope

    val gameProperty = scope.gameProperty
    val buildings    = ReadOnlyListWrapper<BuildingVM>()

    init {
        buildings.bind(gameProperty.objectBinding {
            it!!.mainSettlement.buildings
                .map { BuildingVM(it.key, it.value, this) }
                .asObservable()
        })
    }

    fun build(type: BuildingType) {
        scope.updateGame { game ->
            Game(listOf(game.mainSettlement.build(type)))
        }
    }
}