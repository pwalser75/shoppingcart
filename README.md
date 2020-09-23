# Shopping Cart

**A polyglot programming exercise in many programming languages.** 

The idea is to have one and the same example program written in multiple programming languages, as a reference
on how to implement some common concepts with each featured programming language.

Concepts to show (if supported by the language):
- **Domain modelling** of mutable (Entity) and immutable (Value Object) data models
- **Composite** data models
- **Enumeration** data type
- **Object orientation**
- **Functional programming**
- **Generics**
- **Interfaces, abstract classes**
- Calculus with constrained numbers (2-digits precision)
- Utility functions
- **Exception handling**

## Programming languages

Implementations are available for:

* [Java](./java) (Java 11)
* [Kotlin](./kotlin)
* [Javascript](./javascript) (ECMAScript 6 / 2015)
* [Typescript](./typescript)

Implementations are planned also for:

* **Rust** (under construction)
* **Groovy**

## Domain / Requirements

Exercise: **create a shopping cart, fill it with products, and go to the checkout to have it print a receipt.**

For this you need following primary domain objects:
- `Product` a product **Entity**, identified by a numeric `id`. The product has a `name` and a `price` per `unit`. All of its attributes are *mutable*.
- `ShoppingCart`: a **Domain Object**, containing products in a specific amount. Offers methods to `add` and `remove` products, where the amount of the products will be accumulated.
- `Checkout`: another **Domain Object**, which will list the products in the shopping cart and sum up the price of the products in the cart. Prices will be converted into the reference currency (in this example: `CHF`)

and following supporting domain objects:
- `Unit`: an **Enumeration** of available units for the product: `QUANTITY` (`x`), `KILOGRAMS` (`kg`), `LITER` `(l`).
- `CurrencyAmount`: a **Value Object** for an amount in a specific currency. This object shall be *immutable* and be identified *by value*. Its values are passed on construction time and are *validated* to prevent invalid instances to be created. The amount will be rounded to 2 decimal places.
- `ExchangeRateService`: a **Service** which can convert amounts between different currencies. The exchange rates of the supported currencies are internally configured. Conversion from/to unknown currencies result in an errror stating that the currencies are not supported.
- `Check`: an **Util Class** with functions to validate values using validator functions, with built-in functions for `required` (non-null, non-trivial value), `min` (min value) and `format` (regular expression).  

## Test Scenario:

Given the following products:
- id: **1**, name: **Toblerone 100g**, price per unit: **CHF 2.25**, unit: **quantity**
- id: **2**, name: **Milk**, price per unit: **USD 1.85**, unit: **liter**
- id: **3**, name: **Broccoli**, price per unit: **EUR 0.80**, unit: **kilogram**

and the following exchange rates:
- EUR 1 = CHF 1.0744
- USD 1 = CHF 0.9103
- GBP 1 = CHF 1.1794

when adding following quantities/products to the shopping cart:
- 3 x **Toblerone 100g**
- 2 x **Milk**
- 3 x **Milk**
- 0.450 x **Broccoli**
- 0.163 x **Broccoli**

the checkout will print:
```
3 x Toblerone 100g (id=1, price=CHF 2.25/x) = CHF 6.75
5 l Milk (id=2, price=USD 1.85/l) = CHF 8.42
0.613 kg Broccoli (id=3, price=EUR 0.80/kg) = CHF 0.53
Total: CHF 15.70
```

## Build and Run

### Java

The Java implementation requires **Java 11**. Compile it with [Maven](https://maven.apache.org/):
```bash
mvn
```
This will build an executable **JAR** (Java Archive) in the `target` folder. Execute it from the command line with: 
```bash
java -jar target/shopping-cart-1.0.0-SNAPSHOT.jar
```

### Kotlin

Compile it with [Maven](https://maven.apache.org/):
```bash
mvn
```
This will build an executable **JAR** (Java Archive) in the `target` folder. Execute it from the command line with: 
```bash
java -jar target/shopping-cart-1.0.0-SNAPSHOT.jar
```

### Javascript

No compilation required, as this is a script language.<br>
To run it from the command prompt, install [Node.js](https://nodejs.org), and run the program from the command line: 
```bash
node shopping-cart.js
```

### Typescript

Preparation: in the `typescript` directory, fetch the node dependencies (typescript module) once using `npm install`.

Build (compile Typescript to JavaScript, output directory: `build`):
```bash
npm run tsc
```

To run it from the command prompt, install [Node.js](https://nodejs.org), and run the program from the command line: 
```bash
node build/main.ts
```

To compile and run (see script `start` in `package.json`);
```bash
npm start
```

### Rust

Install Rust: https://www.rust-lang.org/tools/install
```bash
curl --proto '=https' --tlsv1.2 -sSf https://sh.rustup.rs | sh
```

Build (debug executable, for release use the `--release` option):
```bash
cargo build
```

To run, just execute the binary: `./target/debug/shopping-cart` (or `./target/release/shopping-cart` for the release version).

You can also directly build and run with cargo:
```bash
cargo run
```

## Reference implementation

The Javascript solution illustrates a possible implementation:

```javascript
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
```

```javascript
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
```

```javascript
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
```

```javascript
const Unit = {
    QUANTITY: 'x',
    KILOGRAM: 'kg',
    LITER: 'l'
}
```

```javascript
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
```

```javascript
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
```

```javascript
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
```

Test scenario:

```javascript
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
```