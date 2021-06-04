package game.controllers

import game.GameScope
import game.models.BuildingType
import game.models.Buildings
import game.models.Game
import javafx.beans.property.ReadOnlyBooleanWrapper
import javafx.beans.property.ReadOnlyListWrapper
import tornadofx.*

class BuildingVM(val type: BuildingType, val count: Int, controller: BuildingsController) {
    private val building = Buildings.forType(type)

    val itself: BuildingVM
        get() = this
    val costString: String
        get() = building.cost.toString()
    val productionString: String
        get() = building.production.toString()
    val consumptionString: String
        get() = building.consumption.toString()

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
        scope.updateGame { game ->
            Game(listOf(game.mainSettlement.build(type)))
        }
    }
}