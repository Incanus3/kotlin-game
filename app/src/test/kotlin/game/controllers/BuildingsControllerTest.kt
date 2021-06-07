package game.controllers

import game.GameScope
import game.models.*
import io.kotest.core.spec.IsolationMode
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

class BuildingsControllerTest : DescribeSpec({
    isolationMode = IsolationMode.InstancePerLeaf

    val settlement = Settlement(resources = Resources { timber = 150 })
    val game       = Game(listOf(settlement))
    val scope      = GameScope(game)
    val controller = tornadofx.find(BuildingsController::class, scope)

    describe("buildings view") {
        it("allows user to build a building") {
            val farmBefore = controller.buildings.value.find { it.type == BuildingType.FARM }!!

            farmBefore.count.shouldBe(0)

            controller.build(BuildingType.FARM)

            val farmAfter = controller.buildings.value.find { it.type == BuildingType.FARM }!!

            farmAfter.count.shouldBe(1)
        }

        it("correctly shows whether a building can be built") {
            val farmBefore = controller.buildings.value.find { it.type == BuildingType.FARM }!!

            farmBefore.canBeBuilt.shouldBeTrue()

            controller.build(BuildingType.FARM)

            val farmAfter = controller.buildings.value.find { it.type == BuildingType.FARM }!!

            farmAfter.canBeBuilt.shouldBeFalse()
        }
    }
})