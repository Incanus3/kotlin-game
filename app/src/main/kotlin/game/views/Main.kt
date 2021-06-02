package game

import javafx.beans.property.ReadOnlyDoubleWrapper
import javafx.scene.control.TabPane
import tornadofx.*

class MainView: View() {
    override val root = tabpane {
        shortcut("Ctrl+Q") {
            close()
        }

        tabClosingPolicy = TabPane.TabClosingPolicy.UNAVAILABLE

        tab<ResourcesView>()
        tab<BuildingsView>()
    }
}

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

            prefWidth  = 338.0
            prefHeight = 7 * 26.3
        }
    }
}

class BuildingsView: View("Buildings") {
    val controller: BuildingsController by inject()

    override val root = vbox {
        tableview(controller.buildings) {
            readonlyColumn("Type",  BuildingWithCount::type)
            readonlyColumn("Count", BuildingWithCount::count)

            prefWidth  = 200.0
            prefHeight = 7 * 24.0
        }
    }
}