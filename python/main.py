from models.checkout import Checkout
from models.currencyamount import CurrencyAmount
from models.product import Product
from models.shoppingcart import ShoppingCart
from models.unit import Unit
from service.exchangerateservice import ExchangeRateService

if __name__ == '__main__':
    toblerone = Product(1, 'Toblerone 100g', CurrencyAmount('CHF', 2.25), Unit.QUANTITY)
    milk = Product(2, 'Milk', CurrencyAmount('USD', 1.85), Unit.LITER)
    broccoli = Product(3, 'Broccoli', CurrencyAmount('EUR', 0.80), Unit.KILOGRAM)

    shoppingcart = ShoppingCart() \
        .add(3, toblerone) \
        .add(2, milk) \
        .add(3, milk) \
        .add(0.450, broccoli) \
        .add(0.163, broccoli)

    exchange_rate_service = ExchangeRateService()
    checkout = Checkout(exchange_rate_service)

    checkout.checkout(shoppingcart)
