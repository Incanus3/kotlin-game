package game

import kotlin.concurrent.timer
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.SimpleListProperty
import javafx.beans.value.ChangeListener
import tornadofx.*

class MainController: Controller() {
    val settlementProperty = SimpleObjectProperty(Settlement())
    var settlement by settlementProperty

    val resourceListProperty = SimpleListProperty(settlement.resourceList.asObservable())
    var resourceList by resourceListProperty

    init {
        settlementProperty.addListener(ChangeListener { _, _, new: Settlement ->
            resourceList = new.resourceList.asObservable()
        })

        timer(name = "ticker", daemon = true, period = 1000, initialDelay = 1000) {
            settlement = settlement
                .addResource(ResourceType.TIMBER, 100)
                .addResource(ResourceType.STONE,  100)
                .addResource(ResourceType.IRON,   100)

            // fire(StateUpdated(settlement))
        }
    }
}
