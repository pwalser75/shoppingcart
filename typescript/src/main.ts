import {Product} from './model/product'
import {CurrencyAmount} from './model/currency.amount'
import {Unit} from './model/unit'

var toblerone = new Product(1, 'Toblerone 100g', new CurrencyAmount('CHF', 2.25), Unit.QUANTITY);
var milk = new Product(2, 'Milk', new CurrencyAmount('USD', 1.85), Unit.LITER);
var broccoli = new Product(3, 'Broccoli', new CurrencyAmount('EUR', 0.80), Unit.KILOGRAM);

console.log(toblerone)
console.log(`${toblerone}`)
console.log(`${milk}`)
console.log(`${broccoli}`)