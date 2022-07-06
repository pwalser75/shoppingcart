# Shopping Cart

**A polyglot programming exercise in many programming languages.** 

The idea is to have one and the same example program written in multiple programming languages, as a reference
on how to implement some common concepts with each featured programming language.

Concepts to show (if supported by the language):
- **Domain modelling** of mutable (Entity) and immutable (Value Object) data models
- **Composite** data models
- **Enumeration** data type
- **Object orientation**
- **Interfaces, abstract classes**
- **Functional programming**
- **Generics**
- **Calculations** with constrained numbers (2-digits precision)
- **Collections (List/Map)**
- **Utility functions**
- **Vararg arguments**
- **Constructor/Method overloading**
- **Exception handling**

## Programming languages

Implementations are available for:

* [Java](./java) (Java 11)
* [Kotlin](./kotlin)
* [Javascript](./javascript) (ECMAScript 6 / 2015)
* [Typescript](./typescript)
* [Python](./python) (Python 3)

Implementations are planned also for:

* **Rust** (under construction)
* **Go** 
* **Groovy**

## Domain / Requirements

Exercise: **create a shopping cart, fill it with products, and go to the checkout to have it print a receipt.**

For this you need following primary domain objects:
- `Product` a product **Entity**, identified by a numeric `id`. The product has a `name` and a `price` per `unit`. All of its attributes are *mutable*.
- `ShoppingCart`: a **Domain Object**, containing products in a specific amount. Offers methods to `add` and `remove` products, where the amount of the products will be accumulated.
- `Checkout`: another **Domain Object**, which will print a receipt using the shop name, current date/time, list the products in the shopping cart and total price of the purchase. Prices will be converted into the reference currency (in this example: `CHF`)

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
=== TEST SHOP - 2022-07-06 09:48:43.978 ===
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

Build (compile Typescript to JavaScript using **Webpack**, output directory: `dist`):
```bash
npm run build
```

To run it from the command prompt, install [Node.js](https://nodejs.org), and run the program from the command line: 
```bash
node ./dist/bundle.js
```

To compile and run (see script `start` in `package.json`);
```bash
npm start
```

## Python

If not yet preinstalled on your system, download and install Python3 from https://www.python.org/downloads/

To run it from the command prompt:
```bash
python3 main.py
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
