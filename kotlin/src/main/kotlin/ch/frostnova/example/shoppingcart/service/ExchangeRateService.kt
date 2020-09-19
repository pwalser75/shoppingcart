package ch.frostnova.example.shoppingcart.service

import ch.frostnova.example.shoppingcart.model.CurrencyAmount

interface ExchangeRateService {

    companion object {
        const val BASE_CURRENCY = "CHF"
    }

    fun convert(currencyAmount: CurrencyAmount, toCurrency: String): CurrencyAmount
}