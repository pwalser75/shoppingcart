package ch.frostnova.example.shoppingcart.model;

import ch.frostnova.example.shoppingcart.util.Check;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import static ch.frostnova.example.shoppingcart.util.Check.required;
import static java.util.Collections.unmodifiableMap;

public class ShoppingCart {

    private final Map<Product, BigDecimal> items = new HashMap<>();

    public ShoppingCart add(Number amount, Product product) {
        required("amount", amount, Check.min(0));
        required("product", product);
        BigDecimal bigDecimalAmount = new BigDecimal(amount.toString());
        if (items.containsKey(product)) {
            bigDecimalAmount = items.get(product).add(bigDecimalAmount);
        }
        items.put(product, bigDecimalAmount);
        return this;
    }

    public ShoppingCart remove(Product product) {
        items.remove(product);
        return this;
    }

    public Map<Product, BigDecimal> getItems() {
        return unmodifiableMap(items);
    }
}
