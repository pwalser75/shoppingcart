package ch.frostnova.example.shoppingcart

import ch.frostnova.example.shoppingcart.model.CurrencyAmount
import ch.frostnova.example.shoppingcart.model.Product
import ch.frostnova.example.shoppingcart.model.ShoppingCart
import ch.frostnova.example.shoppingcart.service.ExchangeRateService
import java.math.BigDecimal
import java.math.BigDecimal.ZERO

class Checkout(private val exchangeRateService: ExchangeRateService) {

    private val localCurrency = ExchangeRateService.BASE_CURRENCY

    fun checkout(shoppingCart: ShoppingCart) {
        var total = ZERO
        val items: Map<Product, BigDecimal> = shoppingCart.getItems()
        for (product in items.keys) {
            val amount = items[product]
            val price = CurrencyAmount(product.price?.currency ?: localCurrency,
                    amount!!.multiply(product.price?.amount ?: ZERO))
            val priceConverted = exchangeRateService.convert(price, localCurrency)
            println("$amount ${product.unit} $product = $priceConverted")
            total = total.add(priceConverted.amount)
        }
        println("Total: ${CurrencyAmount(localCurrency, total)}")
    }

}