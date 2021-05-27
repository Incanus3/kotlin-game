package game

import tornadofx.*

class MainView : View() {
    val controller: MainController by inject()

    override fun onDock() {
        setWindowMinSize(300, 300)
    }

    override val root = vbox {
        shortcut("Ctrl+Q") {
            close()
        }

        tableview(controller.resources().asObservable()) {
            readonlyColumn("Type",   Resource::type)
            readonlyColumn("Amount", Resource::amount)
        }
    }
}
