package game

import kotlin.concurrent.timer
import javafx.beans.property.SimpleObjectProperty
import javafx.beans.property.ReadOnlyListWrapper
import tornadofx.*

class MainController: Controller() {
    val settlementProperty = SimpleObjectProperty(Settlement())
    var settlement: Settlement by settlementProperty

    val resourceListWithCapacitiesProperty = ReadOnlyListWrapper<ResourceWithCapacity>()

    init {
        resourceListWithCapacitiesProperty.bind(settlementProperty.objectBinding {
            settlementProperty.value.resourceListWithCapacities.asObservable()
        })

        timer(name = "ticker", daemon = true, period = 1000, initialDelay = 1000) {
            settlement = settlement
                .addResource(ResourceType.FOOD,   100)
                .addResource(ResourceType.TIMBER, 100)
                .addResource(ResourceType.STONE,  100)
                .addResource(ResourceType.IRON,   100)
        }
    }
}
