package ch.frostnova.example.shoppingcart.util

import java.math.BigDecimal
import java.util.regex.Pattern

object Check {

    @JvmStatic
    @SafeVarargs
    fun <T> required(property: String, value: T, vararg validators: Validator<in T>): T {
        if (value is String && (value as String).length == 0) {
            throw ValidationException(property, value, "must not be empty")
        }
        for (validator in validators) {
            val error = validator.validate(value)
            if (error != null) {
                throw ValidationException(property, value, error)
            }
        }
        return value
    }

    fun min(min: Number): Validator<Number> {
        return object : Validator<Number> {
            override fun validate(value: Number): String? {
                return if (BigDecimal(value.toString()) < BigDecimal(min.toString()))
                    "must be at least $min" else null
            }
        }
    }

    @JvmOverloads
    fun format(regex: String?, error: String = "invalid format"): Validator<String> {
        val pattern = Pattern.compile(regex)
        return object : Validator<String> {
            override fun validate(value: String): String? {
                return if (!pattern.matcher(value).matches()) error else null
            }
        }
    }

    @FunctionalInterface
    interface Validator<T> {
        fun validate(value: T): String?
    }

    class ValidationException(property: String?, value: Any?, error: String?) : RuntimeException("$property: '$value' $error")
}