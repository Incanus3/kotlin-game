package game

import game.models.BuildingType
import game.models.Game
import game.models.Resources
import game.models.Settlement
import game.views.MainView
import javafx.beans.property.SimpleObjectProperty
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCombination
import tornadofx.*
import kotlin.concurrent.timer

class GameScope(game: Game = Game()): Scope() {
    val gameProperty = SimpleObjectProperty(game)
    var game: Game by gameProperty

    init {
        timer(name = "ticker", daemon = true, period = 1000, initialDelay = 1000) {
            updateGame { it.tick() }
        }
    }

    fun updateGame(op: (Game) -> Game) {
        // TODO: protect this with mutex
        // - this could be done with Mutex.withLock, the problem is that this is a suspend funcion
        game = op.invoke(game)
    }
}

public class GameApp: App(MainView::class) {
    init {
        val settlement = Settlement(
            resources  = Resources { all = 500              },
            capacities = Resources { all = 1000; food = 500 },
        )

        scope = GameScope(Game(listOf(settlement)))

        FX.layoutDebuggerShortcut = KeyCodeCombination(KeyCode.J, KeyCombination.ALT_DOWN)
    }
}