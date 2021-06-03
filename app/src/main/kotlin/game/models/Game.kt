package game

class Game(val settlements: List<Settlement> = listOf(Settlement())) {
    val mainSettlement: Settlement
        get() = settlements.first()

    fun tick(): Game {
        return Game(listOf(mainSettlement.produce()))
    }
}