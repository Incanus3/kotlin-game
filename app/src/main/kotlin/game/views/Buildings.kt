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
                text = Buildings.forType(it).cost
                    .map { "${it.amount} ${it.type}" }.joinToString()
            }

            readonlyColumn("Produces", BuildingWithCount::type).cellFormat {
                // TODO: move this to a view model
                text = Buildings.forType(it).produces
                    .map { "${it.amount} ${it.type}" }.joinToString()
            }

            readonlyColumn("Consumes", BuildingWithCount::type).cellFormat {
                // TODO: move this to a view model
                text = Buildings.forType(it).consumes
                    .map { "${it.amount} ${it.type}" }.joinToString()
            }

            column<BuildingWithCount, Pair<BuildingType, Boolean>>("Build") {
                val buildingType = it.value.type

                @Suppress("UNCHECKED_CAST")
                controller.canBuild(buildingType).objectBinding {
                    Pair(buildingType, it!!)
                } as ObservableValue<Pair<BuildingType, Boolean>>
            }.cellFormat {
                graphic = button("Build") {
                    action { controller.build(it.first) }

                    enableWhen(ReadOnlyBooleanWrapper(it.second))

                    useMaxWidth = true
                }
            }

            prefWidth      = 570.0
            prefHeight     = 7 * 24.0
            selectionModel = null
        }
    }
}