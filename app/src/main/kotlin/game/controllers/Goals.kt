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
            val grouped = it!!.goals.groupBy(Goal::hasBeenMet)
            val met     = grouped.getOrDefault(true,  emptyList()).takeLast(3)
            val unmet   = grouped.getOrDefault(false, emptyList()).take(2)

            (met + unmet).asObservable()
        })
    }
}