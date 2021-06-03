package game

import javafx.beans.property.ReadOnlyBooleanWrapper
import javafx.beans.value.ObservableValue
import tornadofx.*

class BuildingsView: View("Buildings") {
    val controller: BuildingsController by inject()

    override val root = vbox {
        tableview(controller.buildings) {
            readonlyColumn("Type",  BuildingWithCount::type)
            readonlyColumn("Count", BuildingWithCount::count)

            readonlyColumn("Cost", BuildingWithCount::type).cellFormat {
                // TODO: move this to a view model
                val building = Buildings.forType(it)!!

                text = building.cost.map { "${it.amount} ${it.type}" }.joinToString()
            }

            readonlyColumn("Produces", BuildingWithCount::type).cellFormat {
                // TODO: move this to a view model
                val building = Buildings.forType(it)!!

                text = building.produces.map { "${it.amount} ${it.type}" }.joinToString()
            }

            readonlyColumn("Consumes", BuildingWithCount::type).cellFormat {
                // TODO: move this to a view model
                val building = Buildings.forType(it)!!

                text = building.consumes.map { "${it.amount} ${it.type}" }.joinToString()
            }

            column<BuildingWithCount, Pair<BuildingType, Boolean>>("Build") {
                val buildingType = it.value.type
                val canBuild     = controller.canBuild(buildingType)

                @Suppress("UNCHECKED_CAST")
                canBuild.objectBinding {
                    Pair(buildingType, canBuild.value)
                } as ObservableValue<Pair<BuildingType, Boolean>>
            }.cellFormat {
                graphic = button("Build") {
                    action { controller.build(it.first) }

                    enableWhen(ReadOnlyBooleanWrapper(it.second))
                }
            }

            prefWidth      = 570.0
            prefHeight     = 7 * 24.0
            selectionModel = null
        }
    }
}