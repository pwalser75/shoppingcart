package ch.frostnova.example.shoppingcart;

import ch.frostnova.example.shoppingcart.model.CurrencyAmount;
import ch.frostnova.example.shoppingcart.model.Product;
import ch.frostnova.example.shoppingcart.model.ShoppingCart;
import ch.frostnova.example.shoppingcart.service.ExchangeRateService;

import java.math.BigDecimal;
import java.util.Map;

import static ch.frostnova.example.shoppingcart.util.Check.required;

public class Checkout {

    private final ExchangeRateService exchangeRateService;
    private final String localCurrency = ExchangeRateService.BASE_CURRENCY;

    public Checkout(ExchangeRateService exchangeRateService) {
        this.exchangeRateService = required("exchangeRateService", exchangeRateService);
    }

    public void checkout(ShoppingCart shoppingCart) {
        required("shoppingCart", shoppingCart);

        var total = BigDecimal.ZERO;
        Map<Product, BigDecimal> items = shoppingCart.getItems();
        for (Product product : items.keySet()) {
            var amount = items.get(product);
            var price = new CurrencyAmount(product.getPrice().getCurrency(),
                    amount.multiply(product.getPrice().getAmount()));
            var priceConverted = exchangeRateService.convert(price, localCurrency);
            System.out.printf("%s %s %s = %s\n", amount, product.getUnit(), product, priceConverted);
            total = total.add(priceConverted.getAmount());
        }
        System.out.printf("Total: %s\n", new CurrencyAmount(localCurrency, total));
    }
}
