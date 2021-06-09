package game

import game.models.*
import game.views.MainView
import javafx.application.Platform
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
        timer(name = "ticker", daemon = true, period = 300, initialDelay = 1000) {
            // this shouldn't be necessary as we don't update the UI directly, but without this
            // rendering of GoalsView list view cells throws the following exception:
            // java.lang.IllegalStateException: Not on FX application thread; currentThread = ticker
            // this doesn't happen if we add cache {} around the readonly checkbox, but then the
            // checkboxes don't get updated, even though the whole controller.goals list given to
            // the listview changes
            Platform.runLater {
                updateGame { it.tick() }
            }
        }
    }

    fun updateGame(op: (Game) -> Game) {
        // TODO: protect this with mutex
        // - this could be done with Mutex.withLock, but that's a suspend function
        game = op(game)
    }
}

public class GameApp: App(MainView::class) {
    init {
        val settlement = Settlement(
            resources  = Resources { all = 500              },
            capacities = Resources { all = 1000; food = 500 },
        )

        scope = GameScope(Game(
            listOf(settlement),
            listOf(
                Goal("met 1") { true },
                Goal("met 2") { true },
                Goal("met 3") { true },
                Goal("have 1000 timber") { game ->
                    game.resources.forType(ResourceType.TIMBER) >= 1000
                },
                Goal("unmet 1") { false },
                Goal("unmet 2") { false },
                Goal("unmet 3") { false },
            )
        ))

        FX.layoutDebuggerShortcut = KeyCodeCombination(KeyCode.J, KeyCombination.ALT_DOWN)
    }
}