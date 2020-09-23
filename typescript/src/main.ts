import {Product} from './model/product'
import {CurrencyAmount} from './model/currencyAmount'
import {Unit} from './model/unit'
import {ShoppingCart} from './model/shoppingCart'
import {Checkout} from './model/checkout'
import {ExchangeRateServiceImpl} from './service/exchangeRateService'

const toblerone = new Product(1, 'Toblerone 100g', new CurrencyAmount('CHF', 2.25), Unit.QUANTITY);
const milk = new Product(2, 'Milk', new CurrencyAmount('USD', 1.85), Unit.LITER);
const broccoli = new Product(3, 'Broccoli', new CurrencyAmount('EUR', 0.80), Unit.KILOGRAM);

const shoppingCart = new ShoppingCart()
    .add(3, toblerone)
    .add(2, milk)
    .add(3, milk)
    .add(0.450, broccoli)
    .add(0.163, broccoli);

const exchangeRateService = new ExchangeRateServiceImpl();
const checkout = new Checkout(exchangeRateService);

checkout.checkout(shoppingCart);

