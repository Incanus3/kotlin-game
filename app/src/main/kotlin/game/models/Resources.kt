package game.models

enum class ResourceType {
    FOOD, TIMBER, STONE, IRON;

    override fun toString(): String {
        return name.lowercase().replaceFirstChar { it.uppercase() }
    }
}

data class Resource(val type: ResourceType, val amount: Int)