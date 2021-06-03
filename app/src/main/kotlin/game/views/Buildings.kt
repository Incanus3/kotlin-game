package game.views

import game.controllers.BuildingVM
import game.controllers.BuildingsController
import tornadofx.*

class BuildingsView: View("Buildings") {
    val controller: BuildingsController by inject()

    override val root = vbox {
        tableview(controller.buildings) {
            readonlyColumn("Type",     BuildingVM::type)
            readonlyColumn("Count",    BuildingVM::count)
            readonlyColumn("Cost",     BuildingVM::costString)
            readonlyColumn("Produces", BuildingVM::productionString)
            readonlyColumn("Consumes", BuildingVM::consumptionString)
            readonlyColumn("Build",    BuildingVM::itself).cellFormat {
                graphic = button("Build") {
                    action { controller.build(it.type) }

                    enableWhen(it.canBeBuilt)

                    useMaxWidth = true
                }
            }

            prefWidth      = 570.0
            prefHeight     = 7 * 24.0
            selectionModel = null
        }
    }
}