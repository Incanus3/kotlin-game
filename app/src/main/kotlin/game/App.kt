package game

import tornadofx.App
import tornadofx.FX
import tornadofx.UIComponent
import javafx.scene.input.KeyCodeCombination
import javafx.scene.input.KeyCode
import javafx.scene.input.KeyCombination

public class MyApp: App(MainView::class) {
    init {
        FX.layoutDebuggerShortcut = KeyCodeCombination(KeyCode.J, KeyCombination.ALT_DOWN)

    }
}
