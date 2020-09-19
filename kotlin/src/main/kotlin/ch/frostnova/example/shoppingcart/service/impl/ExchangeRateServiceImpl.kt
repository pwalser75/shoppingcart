package ch.frostnova.example.shoppingcart.service.impl

import ch.frostnova.example.shoppingcart.model.CurrencyAmount
import ch.frostnova.example.shoppingcart.service.ExchangeRateService
import ch.frostnova.example.shoppingcart.service.ExchangeRateService.Companion.BASE_CURRENCY
import java.math.BigDecimal
import java.math.MathContext.DECIMAL64
import java.math.RoundingMode.HALF_UP
import java.util.*

class ExchangeRateServiceImpl : ExchangeRateService {

    companion object {
        private val exchangeRates: MutableMap<String, BigDecimal> = HashMap()

        init {
            exchangeRates["EUR"] = BigDecimal.valueOf(1.0744)
            exchangeRates["USD"] = BigDecimal.valueOf(0.9103)
            exchangeRates["GBP"] = BigDecimal.valueOf(1.1794)
        }
    }

    override fun convert(currencyAmount: CurrencyAmount, toCurrency: String): CurrencyAmount {
        return CurrencyAmount(toCurrency,
                currencyAmount.amount.multiply(exchangeRate(currencyAmount.currency, toCurrency)))
    }

    private fun exchangeRate(fromCurrency: String, toCurrency: String): BigDecimal {
        if (fromCurrency == toCurrency) {
            return BigDecimal.ONE
        }
        if (fromCurrency == BASE_CURRENCY) {
            return BigDecimal.ONE.divide(requireExchangeRate(toCurrency), DECIMAL64)
        }
        return if (toCurrency == BASE_CURRENCY) {
            requireExchangeRate(fromCurrency)
        } else exchangeRate(fromCurrency, BASE_CURRENCY)
                .multiply(exchangeRate(BASE_CURRENCY, toCurrency))
                .setScale(2, HALF_UP)
    }

    private fun requireExchangeRate(currency: String): BigDecimal {
        return exchangeRates[currency] ?: throw IllegalArgumentException("Unsupported currency: $currency")
    }
}