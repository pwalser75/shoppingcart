package ch.frostnova.example.shoppingcart.service.impl

import ch.frostnova.example.shoppingcart.model.CurrencyAmount
import ch.frostnova.example.shoppingcart.service.ExchangeRateService
import ch.frostnova.example.shoppingcart.service.ExchangeRateService.Companion.BASE_CURRENCY
import java.math.BigDecimal
import java.math.BigDecimal.ONE
import java.math.MathContext.DECIMAL64

class ExchangeRateServiceImpl : ExchangeRateService {

    companion object {
        private val exchangeRates: Map<String, BigDecimal> = mapOf(
                "EUR" to BigDecimal.valueOf(1.0744),
                "USD" to BigDecimal.valueOf(0.9103),
                "GBP" to BigDecimal.valueOf(1.1794))
    }

    override fun convert(currencyAmount: CurrencyAmount, toCurrency: String): CurrencyAmount {
        return CurrencyAmount(toCurrency,
                currencyAmount.amount.multiply(exchangeRate(currencyAmount.currency, toCurrency)))
    }

    private fun exchangeRate(fromCurrency: String, toCurrency: String): BigDecimal {
        if (fromCurrency == toCurrency) {
            return ONE
        }
        if (fromCurrency == BASE_CURRENCY) {
            return ONE.divide(requireExchangeRate(toCurrency), DECIMAL64)
        }
        if (toCurrency == BASE_CURRENCY) {
            return requireExchangeRate(fromCurrency)
        }
        return exchangeRate(fromCurrency, BASE_CURRENCY)
                .multiply(exchangeRate(BASE_CURRENCY, toCurrency))
    }

    private fun requireExchangeRate(currency: String): BigDecimal {
        return exchangeRates[currency] ?: throw IllegalArgumentException("Unsupported currency: $currency")
    }
}