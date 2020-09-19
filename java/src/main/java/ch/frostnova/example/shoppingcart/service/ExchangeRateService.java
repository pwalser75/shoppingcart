package ch.frostnova.example.shoppingcart.service;

import ch.frostnova.example.shoppingcart.model.CurrencyAmount;

public interface ExchangeRateService {

    String BASE_CURRENCY = "CHF";

    CurrencyAmount convert(CurrencyAmount currencyAmount, String toCurrency);
}
