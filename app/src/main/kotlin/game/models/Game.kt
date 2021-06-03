package game

class Game(val settlements: List<Settlement> = listOf(Settlement())) {
    val mainSettlement: Settlement
        get() = settlements.first()

    fun tick(): Game {
        return Game(listOf(mainSettlement
            .addResource(ResourceType.FOOD,   100)
            .addResource(ResourceType.TIMBER, 100)
            .addResource(ResourceType.STONE,  100)
            .addResource(ResourceType.IRON,   100)
        ))
    }
}