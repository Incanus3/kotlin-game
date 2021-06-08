package game.models

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

class SettlementTest: DescribeSpec({
    val woodcutterLevel = 3
    val quarryLevel     = 1
    val woodcutter      = Buildings.forType(BuildingType.WOODCUTTER)
    val quarry          = Buildings.forType(BuildingType.QUARRY)
    val timberProduction = woodcutter.productionFor(woodcutterLevel).forType(ResourceType.TIMBER)
    val stoneProduction = quarry.productionFor(quarryLevel).forType(ResourceType.STONE)
    val settlement      = Settlement(
        buildings = mapOf(
            BuildingType.WOODCUTTER to woodcutterLevel, BuildingType.QUARRY to quarryLevel
        )
    )

    describe("getProductionOf()") {
        it("should work") {
            settlement.getProductionOf(ResourceType.TIMBER).shouldBe(timberProduction)
            settlement.getProductionOf(ResourceType.STONE ).shouldBe(stoneProduction)
            settlement.getProductionOf(ResourceType.IRON  ).shouldBe(0 )
        }
    }

    describe("produce()") {
        it("should work") {
            val afterwards = settlement.produce().produce().produce()

            afterwards.resources.shouldBe(Resources {
                food = 0; timber = 3 * timberProduction; stone = 3 * stoneProduction
            })
        }
    }

    describe("build()") {
        it("should work") {
            val afterwards = settlement.build(BuildingType.QUARRY)

            afterwards.buildings[BuildingType.QUARRY]!!.shouldBe(2)
        }
    }

    describe("canBuild()") {
        it("should work") {
            settlement.canBuild(BuildingType.FARM).shouldBeFalse()
            settlement.produce().produce().produce().produce()
                .canBuild(BuildingType.FARM).shouldBeTrue()
        }
    }
})