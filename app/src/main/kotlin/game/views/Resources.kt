package game.views

import game.controllers.ResourceVM
import game.controllers.ResourcesController
import tornadofx.View
import tornadofx.readonlyColumn
import tornadofx.tableview
import tornadofx.vbox

class ResourcesView: View("Resources") {
    val controller: ResourcesController by inject()

    override val root = vbox {
        tableview(controller.resourcesWithCapacities) {
            readonlyColumn("Type",       ResourceVM::type)
            readonlyColumn("Production", ResourceVM::production)
            readonlyColumn("Amount",     ResourceVM::amount)
            readonlyColumn("Filled",     ResourceVM::filled).useProgressBar()
            readonlyColumn("Capacity",   ResourceVM::capacity)

            prefWidth      = 338.0
            prefHeight     = 7 * 26.3
            selectionModel = null
        }
    }
}