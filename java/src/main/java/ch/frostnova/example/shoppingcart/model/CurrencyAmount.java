package ch.frostnova.example.shoppingcart.model;

import java.math.BigDecimal;
import java.util.Objects;

import static ch.frostnova.example.shoppingcart.util.Check.format;
import static ch.frostnova.example.shoppingcart.util.Check.min;
import static ch.frostnova.example.shoppingcart.util.Check.required;
import static java.math.RoundingMode.HALF_UP;

public class CurrencyAmount {

    private final String currency;
    private final BigDecimal amount;

    public CurrencyAmount(String currency, BigDecimal amount) {
        this.currency = required("currency", currency, format("[A-Z]{3}", "must be a currency code"));
        this.amount = required("amount", amount, min(0)).setScale(2, HALF_UP);
    }

    public CurrencyAmount(String currency, Number amount) {
        this(currency, new BigDecimal(required("amount", amount).toString()));
    }

    public String getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return String.format("%s %s", currency, amount);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CurrencyAmount that = (CurrencyAmount) o;
        return Objects.equals(currency, that.currency) &&
                Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(currency, amount);
    }
}
