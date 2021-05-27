package game

import tornadofx.*

class MainController: Controller() {
    val settlement = Settlement()

    fun resources(): List<Resource> {
        return settlement.resources.map { Resource(it.key, it.value) }
    }
}
