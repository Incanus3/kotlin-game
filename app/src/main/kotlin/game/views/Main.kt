package game

import javafx.beans.property.ReadOnlyDoubleWrapper
import tornadofx.*

class MainView: View() {
    override val root = tabpane {
        tab<ResourcesView>()
        tab<BuildingsView>()
    }
}

class ResourcesView: View("Resources") {
    val controller: ResourcesController by inject()

    override val root = vbox {
        shortcut("Ctrl+Q") {
            close()
        }

        tableview(controller.resourceListWithCapacitiesProperty) {
            readonlyColumn("Type",   ResourceWithCapacity::type)
            readonlyColumn("Amount", ResourceWithCapacity::amount)

            column<ResourceWithCapacity, Number>("Filled") {
                ReadOnlyDoubleWrapper(it.value.amount / it.value.capacity.toDouble())
            }.useProgressBar()

            readonlyColumn("Capacity", ResourceWithCapacity::capacity)

            prefWidth  = 338.0
            prefHeight = 7 * 26.3
        }
    }
}

class BuildingsView: View("Buildings") {
    override val root = stackpane { text("tady budou budovy") }
}