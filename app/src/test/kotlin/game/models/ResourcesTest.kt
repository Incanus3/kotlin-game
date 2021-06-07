package game.models

import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.booleans.shouldBeFalse
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe

class ResourcesTest: DescribeSpec({
    val resources = Resources { food = 50; timber = 100; stone = 200 }

    describe("forType()") {
        it("should work") {
            resources.forType(ResourceType.TIMBER).shouldBe(100)
        }
    }

    describe("toString()") {
        it("should work") {
            resources.toString().shouldBe("50 Food, 100 Timber, 200 Stone")
        }
    }

    describe("toList()") {
        it("should work") {
            resources.toList().shouldBe(listOf(
                Resource(ResourceType.FOOD,   50 ),
                Resource(ResourceType.TIMBER, 100),
                Resource(ResourceType.STONE,  200),
                Resource(ResourceType.IRON,   0  ),
            ))
        }
    }

    describe("map()") {
        it("should work") {
            resources.map { it.amount }.shouldBe(listOf(50, 100, 200, 0))
        }
    }

    describe("add()") {
        it("should add the resource") {
            resources.add(ResourceType.FOOD,50,1000).shouldBe(
                Resources { food = 100; timber = 100; stone = 200 }
            )
        }

        it("should should cap the resulting amount") {
            resources.add(ResourceType.STONE,200,300).shouldBe(
                Resources { food = 50; timber = 100; stone = 300 }
            )
        }

    }

    describe("equals()") {
        it("should work") {
            resources.shouldBe(   Resources { food = 50; timber = 100; stone = 200 })
            resources.shouldNotBe(Resources { food = 50; timber = 200; iron  = 100 })
        }
    }

    describe("plus()") {
        it("should work") {
            (resources +  Resources { food = 100; timber = 100 })
                .shouldBe(Resources { food = 150; timber = 200; stone = 200 })
        }
    }

    describe("minus()") {
        it("should work") {
            (resources -  Resources { food =  0; timber = 100; stone = 100 })
                .shouldBe(Resources { food = 50; timber =   0; stone = 100 })
        }
    }

    describe("times()") {
        it("should work") {
            (resources * 2 ).shouldBe(Resources { food = 100; timber = 200; stone = 400 })
        }
    }

    describe("cap(maximum: Int)") {
        it("should work") {
            (resources * 3).cap(500)
                .shouldBe(Resources { food = 150; timber = 300; stone = 500 })
        }
    }

    describe("cap(maxima: Resources)") {
        it("should work") {
            val maxima = Resources { timber = 200; stone = 500 }

            (resources * 3).cap(maxima).shouldBe(maxima)
        }
    }

    describe("cover()") {
        it("should work") {
            resources.cover(Resources { food =   0; timber = 100; stone = 100 }).shouldBeTrue()
            resources.cover(Resources { food = 100; timber = 100; stone = 100 }).shouldBeFalse()
        }
    }
})