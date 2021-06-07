package game.controllers

import game.GameScope
import game.models.*
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class ResourcesControllerTest : DescribeSpec({
    val woodcutterCount  = 3
    val woodcutter       = Buildings.forType(BuildingType.WOODCUTTER)
    val buildings        = mapOf(BuildingType.WOODCUTTER to woodcutterCount)
    val settlement       = Settlement(buildings = buildings)
    val game             = Game(listOf(settlement))
    val timberCapacity   = game.mainSettlement.capacities.forType(ResourceType.TIMBER)
    val timberProduction = woodcutterCount * woodcutter.production.forType(ResourceType.TIMBER)

    describe("resourcesWithCapacities") {
        it("should correctly represent current state of resources") {
            val scope      = GameScope(game)
            val controller = tornadofx.find(ResourcesController::class, scope)

            val timberBefore = controller.resourcesWithCapacities.value
                .find { it.type == ResourceType.TIMBER }!!

            timberBefore.amount.shouldBe(0)
            timberBefore.filled.shouldBe(0)
            timberBefore.capacity.shouldBe(timberCapacity)
            timberBefore.production.shouldBe(timberProduction)

            scope.updateGame { it.tick() }

            val timberAfter = controller.resourcesWithCapacities.value
                .find { it.type == ResourceType.TIMBER }!!

            timberAfter.amount.shouldBe(timberProduction)
            timberAfter.filled.shouldBe(timberAfter.amount.toFloat() / timberCapacity)
            timberAfter.capacity.shouldBe(timberCapacity)
            timberBefore.production.shouldBe(timberProduction)
        }
    }
})