package game.models

class Game(
    private val settlements: List<Settlement> = listOf(Settlement()),
    public  val goals:       List<Goal>       = emptyList(),
) {
    val mainSettlement: Settlement
        get() = settlements.first()
    val resources: Resources
        get() = settlements.map(Settlement::resources).reduce(Resources::plus)

    fun withSettlements(settlements: List<Settlement>) = Game(settlements, goals)

    fun tick(): Game {
        return Game(
            listOf(mainSettlement.produce()),
            goals.map { it.check(this) },
        )
    }
}