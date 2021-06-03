package game

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

public class MyApp: App(MainView::class) {
    init {
        scope = GameScope()

        FX.layoutDebuggerShortcut = KeyCodeCombination(KeyCode.J, KeyCombination.ALT_DOWN)
    }
}