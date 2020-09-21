package ch.frostnova.example.shoppingcart.service.impl;

import ch.frostnova.example.shoppingcart.model.CurrencyAmount;
import ch.frostnova.example.shoppingcart.service.ExchangeRateService;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static java.math.BigDecimal.ONE;
import static java.math.MathContext.DECIMAL64;

public class ExchangeRateServiceImpl implements ExchangeRateService {

    private static final Map<String, BigDecimal> exchangeRates = new HashMap<>();

    static {
        exchangeRates.put("EUR", BigDecimal.valueOf(1.0744));
        exchangeRates.put("USD", BigDecimal.valueOf(0.9103));
        exchangeRates.put("GBP", BigDecimal.valueOf(1.1794));
    }

    @Override
    public CurrencyAmount convert(CurrencyAmount currencyAmount, String toCurrency) {
        return new CurrencyAmount(toCurrency,
                currencyAmount.getAmount().multiply(exchangeRate(currencyAmount.getCurrency(), toCurrency)));
    }

    private BigDecimal exchangeRate(String fromCurrency, String toCurrency) {
        if (Objects.equals(fromCurrency, toCurrency)) {
            return ONE;
        }
        if (fromCurrency.equals(BASE_CURRENCY)) {
            return ONE.divide(requireExchangeRate(toCurrency), DECIMAL64);
        }
        if (toCurrency.equals(BASE_CURRENCY)) {
            return requireExchangeRate(fromCurrency);
        }
        return exchangeRate(fromCurrency, BASE_CURRENCY)
                .multiply(exchangeRate(BASE_CURRENCY, toCurrency));
    }

    private BigDecimal requireExchangeRate(String currency) {
        return Optional.ofNullable(exchangeRates.get(currency))
                .orElseThrow(() -> new IllegalArgumentException("Unsupported currency: " + currency));
    }
}
