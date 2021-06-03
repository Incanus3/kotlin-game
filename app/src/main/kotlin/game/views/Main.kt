package game

import tornadofx.*

class MainView: View() {
    override val root = vbox {
        shortcut("Ctrl+Q") {
            close()
        }

        label("Resources") {
            style {
                fontSize   = 20.px
                paddingAll = 10
            }
        }

        add(find<ResourcesView>())

        label("Buildings") {
            style {
                fontSize   = 20.px
                paddingAll = 10
            }
        }

        add(find<BuildingsView>())
    }
}