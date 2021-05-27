package game

import tornadofx.App
import tornadofx.FX
import tornadofx.UIComponent
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination
import kotlin.concurrent.thread
import kotlin.concurrent.timer

public class MyApp: App(MainView::class) {
    init {
        FX.layoutDebuggerShortcut = KeyCodeCombination(KeyCode.J, KeyCombination.ALT_DOWN)

        // thread(start = true, isDaemon = true) {
        //     while (true) {
        //         println("tick")
        //         Thread.sleep(1000)
        //     }
        // }

    }

    override fun onBeforeShow(view: UIComponent) {
        timer(name = "ticker", daemon = true, period = 1000, initialDelay = 1000) {
            println("tick")
        }
    }
}
