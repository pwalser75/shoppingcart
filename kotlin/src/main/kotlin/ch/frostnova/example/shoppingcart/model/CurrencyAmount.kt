package ch.frostnova.example.shoppingcart.model

import ch.frostnova.example.shoppingcart.util.Check.format
import ch.frostnova.example.shoppingcart.util.Check.min
import ch.frostnova.example.shoppingcart.util.Check.required
import java.math.BigDecimal
import java.math.RoundingMode.HALF_UP
import java.util.*

class CurrencyAmount(currency: String, amount: BigDecimal, test: String = "Hello") {

    val currency: String = required("currency", currency, format("[A-Z]{3}", "must be a currency code"))
    val amount: BigDecimal = required("amount", amount, min(0)).setScale(2, HALF_UP)

    constructor(currency: String, amount: Number) : this(currency, BigDecimal(required<Number>("amount", amount).toString()))

    override fun toString(): String {
        return "$currency $amount"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CurrencyAmount

        if (currency != other.currency) return false
        if (amount != other.amount) return false

        return true
    }

    override fun hashCode(): Int {
        return Objects.hash(currency, amount)
    }
}