package ch.frostnova.example.shoppingcart.model

enum class Unit(private val displayName: String) {

    QUANTITY("x"),
    KILOGRAM("kg"),
    LITER("l");

    override fun toString(): String {
        return displayName
    }
}