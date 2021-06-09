package game.views

import game.controllers.BuildingVM
import game.controllers.BuildingsController
import javafx.geometry.Pos
import tornadofx.*
import tornadofx.Stylesheet.Companion.tableCell
import tornadofx.Stylesheet.Companion.tableView

class BuildingsView: View("Buildings") {
    private val controller: BuildingsController by inject()

    override val root = vbox {
        tableview(controller.buildings) {
            readonlyColumn("Type",         BuildingVM::type)
            readonlyColumn("Level",        BuildingVM::level)
            readonlyColumn("Produces",     BuildingVM::currentProduction)
            readonlyColumn("Consumes",     BuildingVM::currentConsumption)
            readonlyColumn("Will produce", BuildingVM::nextLvlProduction)
            readonlyColumn("Will consume", BuildingVM::nextLvlConsumption)
            readonlyColumn("Cost",         BuildingVM::nextLvlCost)
            readonlyColumn("Build",        BuildingVM::itself).cellFormat {
                graphic = button("Build") {
                    action { controller.build(it.type) }

                    enableWhen(it.canBeBuiltProperty)

                    useMaxWidth = true
                }
            }

            selectionModel = null

            stylesheet {
                tableView {
                    prefWidth  = 765.px
                    prefHeight = 24.px + 4 * 35.px
                }

                tableCell {
                    alignment = Pos.CENTER_LEFT
                }
            }
        }
    }
}