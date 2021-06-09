package game.views

import game.controllers.GoalsController
import javafx.beans.property.Property
import javafx.beans.property.ReadOnlyBooleanWrapper
import javafx.event.EventTarget
import javafx.geometry.Insets
import javafx.scene.control.CheckBox
import tornadofx.*

class GoalsView : View("My View") {
    private fun EventTarget.readonlyCheckbox(text: String, property: Property<Boolean>): CheckBox {
        return checkbox(text, property) {
            isDisable = true

            style {
                opacity = 1.0
            }
        }
    }

    private val controller: GoalsController by inject()

    override val root = vbox {
        // we can't simply render the checkboxes in a controller.goals.forEach
        // because controller.goals itself needs to be observed
        // there should be a way to do this generically (without rendering a listview),
        // but I don't know of it and in this case listview is probably the right choice
        listview(controller.goals) {
            cellFormat {
                graphic = readonlyCheckbox(it.text, ReadOnlyBooleanWrapper(it.hasBeenMet))
            }

            prefHeight = 5 * 25.0
        }

        padding = Insets(0.0, 10.0, 10.0, 10.0)
    }
}