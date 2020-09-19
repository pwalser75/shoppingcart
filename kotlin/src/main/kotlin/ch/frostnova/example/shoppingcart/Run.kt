package ch.frostnova.example.shoppingcart

import ch.frostnova.example.shoppingcart.model.CurrencyAmount
import ch.frostnova.example.shoppingcart.model.Product
import ch.frostnova.example.shoppingcart.model.ShoppingCart
import ch.frostnova.example.shoppingcart.model.Unit.*
import ch.frostnova.example.shoppingcart.service.impl.ExchangeRateServiceImpl

object Run {

    @JvmStatic
    fun main(args: Array<String>) {

        val toblerone = Product(1L, "Toblerone 100g", CurrencyAmount("CHF", 2.25), QUANTITY)
        val milk = Product(2L, "Milk", CurrencyAmount("USD", 1.85), LITER)
        val broccoli = Product(3L, "Broccoli", CurrencyAmount("EUR", 0.80), KILOGRAM)

        val shoppingCart = ShoppingCart()
                .add(3, toblerone)
                .add(2, milk)
                .add(3, milk)
                .add(0.450, broccoli)
                .add(0.163, broccoli)

        val exchangeRateService = ExchangeRateServiceImpl()
        val checkout = Checkout(exchangeRateService)
        checkout.checkout(shoppingCart)
    }
}