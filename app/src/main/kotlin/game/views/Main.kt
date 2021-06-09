package game.views

import javafx.event.EventTarget
import javafx.scene.control.Label
import tornadofx.*

class MainView: View() {
    private fun EventTarget.customLabel(text: String): Label {
        return label(text) {
            style {
                fontSize   = 20.px
                paddingAll = 10
            }
        }
    }

    override val root = vbox {
        shortcut("Ctrl+Q") { close() }

        customLabel("Resources")
        add(find<ResourcesView>())

        customLabel("Buildings")
        add(find<BuildingsView>())

        customLabel("Goals")
        add(find<GoalsView>())
    }
}