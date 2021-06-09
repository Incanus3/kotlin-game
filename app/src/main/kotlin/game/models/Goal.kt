package game.models

class Goal(val text: String, val hasBeenMet: Boolean = false, val condition: (Game) -> Boolean) {
    fun check(game: Game) = Goal(text, hasBeenMet || condition(game), condition)
}