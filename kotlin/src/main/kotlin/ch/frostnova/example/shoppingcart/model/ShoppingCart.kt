package ch.frostnova.example.shoppingcart.model

import ch.frostnova.example.shoppingcart.util.Check.min
import ch.frostnova.example.shoppingcart.util.Check.required
import java.math.BigDecimal
import java.math.BigDecimal.ZERO
import java.util.*

class ShoppingCart {
    private val items: MutableMap<Product, BigDecimal> = HashMap()

    fun add(amount: Number, product: Product): ShoppingCart {
        required("amount", amount, min(0))
        items[product] = BigDecimal(amount.toString()).add(items[product] ?: ZERO)
        return this
    }

    fun remove(product: Product?): ShoppingCart {
        items.remove(product)
        return this
    }

    //TODO: return as immutable map
    fun getItems(): MutableMap<Product, BigDecimal> {
        return items
    }
}