package ch.frostnova.example.shoppingcart;

import ch.frostnova.example.shoppingcart.model.CurrencyAmount;
import ch.frostnova.example.shoppingcart.model.Product;
import ch.frostnova.example.shoppingcart.model.ShoppingCart;
import ch.frostnova.example.shoppingcart.service.impl.ExchangeRateServiceImpl;

import static ch.frostnova.example.shoppingcart.model.Unit.KILOGRAM;
import static ch.frostnova.example.shoppingcart.model.Unit.LITER;
import static ch.frostnova.example.shoppingcart.model.Unit.QUANTITY;

public class Run {

    public static void main(String[] args) {

        var toblerone = new Product(1L, "Toblerone 100g", new CurrencyAmount("CHF", 2.25), QUANTITY);
        var milk = new Product(2L, "Milk", new CurrencyAmount("USD", 1.85), LITER);
        var broccoli = new Product(3L, "Broccoli", new CurrencyAmount("EUR", 0.80), KILOGRAM);

        var shoppingCart = new ShoppingCart()
                .add(3, toblerone)
                .add(2, milk)
                .add(3, milk)
                .add(0.450, broccoli)
                .add(0.163, broccoli);

        var exchangeRateService = new ExchangeRateServiceImpl();
        var checkout = new Checkout(exchangeRateService);

        checkout.checkout(shoppingCart);
    }
}
