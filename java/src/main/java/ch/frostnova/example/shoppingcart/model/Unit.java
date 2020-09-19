package ch.frostnova.example.shoppingcart.model;

public enum Unit {

    QUANTITY("x"),
    KILOGRAM("kg"),
    LITER("l");

    private final String displayName;

    Unit(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return displayName;
    }
}
