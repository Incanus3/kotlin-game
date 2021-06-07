package game.models

enum class ResourceType {
    FOOD, TIMBER, STONE, IRON;

    override fun toString(): String {
        return name.lowercase().replaceFirstChar { it.uppercase() }
    }
}

data class Resource(val type: ResourceType, val amount: Int)

data class ResourcesBuilder(
    var food: Int = 0, var timber: Int = 0, var stone: Int = 0, var iron: Int = 0
) {
    var all: Int
        get() = throw UnsupportedOperationException()
        set(value: Int) {
            food   = value
            timber = value
            stone  = value
            iron   = value
        }

    fun toMap(): Map<ResourceType, Int> {
        return mapOf(
            ResourceType.FOOD   to food,
            ResourceType.TIMBER to timber,
            ResourceType.STONE  to stone,
            ResourceType.IRON   to iron,
        )
    }
}

class Resources {
    private val resources: Map<ResourceType, Int>

    constructor(resources: Map<ResourceType, Int>? = null) {
        this.resources = resources ?: ResourceType.values().associateWith { 0 }
    }

    constructor(initializer: ResourcesBuilder.() -> Unit) {
        this.resources = ResourcesBuilder().apply(initializer).toMap()
    }

    fun forType(type: ResourceType): Int {
        return resources.getOrDefault(type, 0)
    }

    override fun toString(): String {
        return resources.filterValues { it > 0 }.map { "${it.value} ${it.key}" }.joinToString()
    }

    fun toList(): List<Resource> {
        return resources.map { Resource(it.key, it.value) }
    }

    fun <T> map(op: (Resource) -> T): List<T> {
        return toList().map(op)
    }

    fun add(type: ResourceType, amount: Int, upTo: Int): Resources {
        return Resources(resources + (type to minOf(forType(type) + amount, upTo)))
    }

    override fun equals(other: Any?): Boolean {
        return other is Resources && resources == other.resources
    }
    // TODO: implement hashCode()

    operator fun plus(other: Resources): Resources {
        return Resources(ResourceType.values().associateWith {
            forType(it) + other.forType(it)
        })
    }

    operator fun minus(other: Resources): Resources {
        return Resources(ResourceType.values().associateWith {
            forType(it) - other.forType(it)
        })
    }

    operator fun times(multiplier: Int): Resources {
        return Resources(
            resources.mapValues { it.value * multiplier }
        )
    }

    fun cap(maximum: Int): Resources {
        return Resources(
           resources.mapValues { minOf(it.value, maximum) }
        )
    }

    fun cap(maxima: Resources): Resources {
        return Resources(
            resources.mapValues { minOf(it.value, maxima.forType(it.key)) }
        )
    }

    fun cover(other: Resources): Boolean {
        return resources.all { it.value >= other.forType(it.key) }
    }
}