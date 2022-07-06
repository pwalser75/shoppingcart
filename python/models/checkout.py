from datetime import datetime

from models.currencyamount import CurrencyAmount
from util.check import required


class Checkout:
    def __init__(self, exchange_rate_service):
        self.exchange_rate_service = exchange_rate_service
        self.local_currency = exchange_rate_service.base_currency

    def checkout(self, shopping_cart):
        required('product', shopping_cart)

        print(f'=== PYTHON SHOP - {datetime.now()} ===')

        total = 0
        for product, amount in shopping_cart.list().items():
            price = CurrencyAmount(product.price.currency, product.price.amount * amount)
            price_converted = self.exchange_rate_service.convert(price, self.local_currency)
            print(f'{amount} {product.unit} {product} = {price_converted}')
            total += price_converted.amount

        print(f'Total: {CurrencyAmount(self.local_currency, total)}')
