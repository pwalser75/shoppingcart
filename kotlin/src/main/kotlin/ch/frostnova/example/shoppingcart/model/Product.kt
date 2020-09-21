package ch.frostnova.example.shoppingcart.model

class Product : Entity<Long?> {
    var name: String? = null
    var price: CurrencyAmount? = null
    var unit: Unit? = null

    constructor()

    constructor(id: Long, name: String, price: CurrencyAmount, unit: Unit = Unit.QUANTITY) {
        this.id = id
        this.name = name
        this.price = price
        this.unit = unit
    }

    override fun toString(): String {
        return String.format("%s (id=%s, price=%s/%s)", name, id, price, unit)
    }
}