package ch.frostnova.example.shoppingcart.model;

import static ch.frostnova.example.shoppingcart.util.Check.required;

public class Product extends Entity<Long> {

    private String name;
    private CurrencyAmount price;
    private Unit unit;

    public Product() {

    }

    public Product(Long id, String name, CurrencyAmount price, Unit unit) {
        setId(required("id", id));
        this.name = required("name", name);
        this.price = required("price", price);
        this.unit = required("unit", unit);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public CurrencyAmount getPrice() {
        return price;
    }

    public void setPrice(CurrencyAmount price) {
        this.price = price;
    }

    public Unit getUnit() {
        return unit;
    }

    public void setUnit(Unit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return String.format("%s (id=%s, price=%s/%s)", name, getId(), price, unit);
    }
}
