package game

enum class ResourceType {
    TIMBER, STONE, IRON
}

data class Resource(val type: ResourceType, val amount: Int)

class Settlement {
    val resources = mutableMapOf(
        ResourceType.TIMBER to 0,
        ResourceType.STONE  to 0,
        ResourceType.IRON   to 0
    )

    fun addResource(type: ResourceType, amount: Int) {
        resources[type] = resources[type]!! + amount
    }
}
