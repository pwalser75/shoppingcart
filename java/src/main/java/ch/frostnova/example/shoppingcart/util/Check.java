package ch.frostnova.example.shoppingcart.util;

import java.math.BigDecimal;
import java.util.regex.Pattern;

public final class Check {

    private Check() {

    }

    @SafeVarargs
    public static <T> T required(String property, T value, Validator<? super T>... validators) {
        if (value == null) {
            throw new ValidationException(property, "is required");
        }
        if (value instanceof String && ((String) value).length() == 0) {
            throw new ValidationException(property, value, "must not be empty");
        }
        for (Validator<? super T> validator : validators) {
            String error = validator.validate(value);
            if (error != null) {
                throw new ValidationException(property, value, error);
            }
        }
        return value;
    }

    public static Validator<Number> min(Number min) {
        return value -> new BigDecimal(value.toString()).compareTo(new BigDecimal(min.toString())) < 0
                ? String.format("must be at least %s", min) : null;
    }

    public static Validator<String> format(String regex) {
        return format(regex, "invalid format");
    }

    public static Validator<String> format(String regex, String error) {
        Pattern pattern = Pattern.compile(regex);
        return value -> !pattern.matcher(value).matches() ? error : null;
    }

    @FunctionalInterface
    public interface Validator<T> {
        String validate(T value);
    }

    public static final class ValidationException extends RuntimeException {

        public ValidationException(String property, String error) {
            super(String.format("%s %s", property, error));
        }

        public ValidationException(String property, Object value, String error) {
            super(String.format("%s: '%s' %s", property, value, error));
        }
    }
}