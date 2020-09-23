class Product {

    constructor(id, name, price, unit) {
        this.id = Check.required('id', id);
        this.name = Check.required('name', name);
        this.price = Check.required('price', price);
        this.unit = Check.required('unit', unit);
    }

    toString() {
        return `${this.name} (id=${this.id}, price=${this.price}/${this.unit})`;
    }
}

class ShoppingCart {

    constructor() {
        this.items = [];
    }

    add(amount, product) {
        Check.required('amount', amount, Check.isNumber(), Check.min(0));
        Check.required('product', product);
        var existing = this.items.find(i => i.product.id == product.id);
        if (existing) {
            existing.amount += amount;
        } else {
            this.items.push({
                amount: amount,
                product: product
            });
        }
        return this;
    }

    remove(product) {
        if (product) {
            var existing = this.items.find(i => i.product.id == product.id);
            if (existing) {
                this.items.splice(this.items.indexOf(existing), 1);
            }
        }
        return this;
    }

    toString() {
        return `${this.items.length} items in the shopping cart`;
    }
}

class Checkout {

    constructor(exchangeRateService) {
        this.exchangeRateService =  Check.required('exchangeRateService', exchangeRateService);
        this.localCurrency = this.exchangeRateService.baseCurrency;
    }

    checkout(shoppingCart) {
        Check.required('shoppingCart', shoppingCart);
        var total = 0;
        for (var item of shoppingCart.items) {
            var price = new CurrencyAmount(item.product.price.currency, item.amount * item.product.price.amount);
            var priceConverted = this.exchangeRateService.convert(price, this.localCurrency);
            console.log(`${item.amount} ${item.product.unit} ${item.product} = ${priceConverted}`);
            total += priceConverted.amount;
        }
        console.log("Total: " + new CurrencyAmount(this.localCurrency, total));
    }
}
const Unit = {
    QUANTITY: 'x',
    KILOGRAM: 'kg',
    LITER: 'l'
}

class CurrencyAmount {

    constructor(currency, amount) {
        this.currency = Check.required('currency', currency, Check.format(/[A-Z]{3}/, 'must be a currency code'));
        this.amount = CurrencyAmount.round(Check.required("amount", amount, Check.isNumber(), Check.min(0)));
    }

    static round(amount) {
        return Math.round((amount + Number.EPSILON) * 100) / 100;
    }

    toString() {
        return this.currency + " " + this.amount.toFixed(2);
    }
}

class ExchangeRateService {

    constructor() {
        this.baseCurrency = 'CHF';
        this.exchangeRates = {
            'EUR': 1.0744,
            'USD': 0.9103,
            'GBP': 1.1794
        };
    }

    convert(currencyAmount, toCurrency) {
        return new CurrencyAmount(toCurrency,
            currencyAmount.amount * this.exchangeRate(currencyAmount.currency, toCurrency));
    }

    exchangeRate(fromCurrency, toCurrency) {
        if (fromCurrency == toCurrency) {
            return 1;
        }
        if (fromCurrency == this.baseCurrency) {
            return 1 / this.requireExchangeRate(toCurrency);
        }
        if (toCurrency == this.baseCurrency) {
            return this.requireExchangeRate(fromCurrency);
        }
        return this.exchangeRate(fromCurrency, this.baseCurrency) * this.exchangeRate(this.baseCurrency, toCurrency);
    }

    requireExchangeRate(currency) {
        var exchangeRate = this.exchangeRates[currency];
        if (!exchangeRate) {
            throw `unsupported currency: ${currency}`;
        }
        return exchangeRate;
    }
}

class Check {
    static required(property, value, ...validators) {
        if (value == null || value == undefined) {
            throw `${property} is required`;
        }
        if (typeof value == 'string' && value.length == 0) {
            throw `${property} must not be empty`;
        }
        for (var validator of validators) {
            var error = validator(value);
            if (error) {
                throw `${property}: '${value}' ${error}`;
            }
        }
        return value;
    }

    static isNumber() {
        return value => (typeof value != 'number') ? 'must be a number' : null;
    }

    static min(min) {
        return value => (value < min) ? `must be at least ${min}` : null;
    }

    static format(regex, error) {
        return value => !value.match(regex) ? error ? error : 'invalid format' : null;
    }
}

var toblerone = new Product(1, 'Toblerone 100g', new CurrencyAmount('CHF', 2.25), Unit.QUANTITY);
var milk = new Product(2, 'Milk', new CurrencyAmount('USD', 1.85), Unit.LITER);
var broccoli = new Product(3, 'Broccoli', new CurrencyAmount('EUR', 0.80), Unit.KILOGRAM);

var shoppingCart = new ShoppingCart()
    .add(3, toblerone)
    .add(2, milk)
    .add(3, milk)
    .add(0.450, broccoli)
    .add(0.163, broccoli);

const exchangeRateService = new ExchangeRateService();
const checkout = new Checkout(exchangeRateService);

checkout.checkout(shoppingCart);