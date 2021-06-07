package game.models

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe

class SettlementTest: DescribeSpec({
    val settlement = Settlement(
        buildings = mapOf(BuildingType.WOODCUTTER to 3, BuildingType.QUARRY to 1)
    )

    describe("getProductionOf()") {
        it("should work") {
            settlement.getProductionOf(ResourceType.TIMBER).shouldBe(30)
            settlement.getProductionOf(ResourceType.STONE ).shouldBe(10)
            settlement.getProductionOf(ResourceType.IRON  ).shouldBe(0 )
        }
    }

    describe("produce()") {
        it("should work") {
            val afterwards = settlement.produce().produce().produce()

            afterwards.resources.shouldBe(Resources { food = 0; timber = 90; stone = 30 })
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