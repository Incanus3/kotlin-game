package game.controllers

import game.GameScope
import game.models.Goal
import javafx.beans.property.ReadOnlyListWrapper
import tornadofx.Controller
import tornadofx.asObservable
import tornadofx.objectBinding

class GoalsController: Controller() {
    override val scope = super.scope as GameScope

    val goals = ReadOnlyListWrapper<Goal>().also {
        it.bind(scope.gameProperty.objectBinding {
            it!!.goals.asObservable()
        })
    }
}