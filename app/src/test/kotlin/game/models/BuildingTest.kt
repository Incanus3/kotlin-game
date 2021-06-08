package game.models

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe

class BuildingTest : DescribeSpec({
    val building = Farm()

    describe("costFor(level)") {
        it("should work") {
            building.costFor(3).shouldBe(building.baseCost * 3)
        }
    }

    describe("productionFor(level)") {
        it("should work") {
            building.productionFor(3).shouldBe(building.baseProduction * 2 * 2)
        }
    }

    describe("consumptionFor(level)") {
        it("should work") {
            building.consumptionFor(3).shouldBe(building.baseConsumption * 2 * 2)
        }
    }
})