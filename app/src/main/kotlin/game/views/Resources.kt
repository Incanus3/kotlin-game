package game

import javafx.beans.property.ReadOnlyDoubleWrapper
import tornadofx.*

class ResourcesView: View("Resources") {
    val controller: ResourcesController by inject()

    override val root = vbox {
        tableview(controller.resourcesWithCapacities) {
            readonlyColumn("Type",   ResourceWithCapacity::type)
            readonlyColumn("Amount", ResourceWithCapacity::amount)

            column<ResourceWithCapacity, Number>("Filled") {
                ReadOnlyDoubleWrapper(it.value.amount / it.value.capacity.toDouble())
            }.useProgressBar()

            readonlyColumn("Capacity", ResourceWithCapacity::capacity)

            prefWidth      = 338.0
            prefHeight     = 7 * 26.3
            selectionModel = null
        }
    }
}