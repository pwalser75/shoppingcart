from util.check import required, is_number, minimum, is_currency


class CurrencyAmount:
    def __init__(self, currency: str, amount: float):
        self.currency = required('currency', currency, is_currency())
        self.amount = required('amount', amount, is_number(), minimum(0))

    def __str__(self):
        return f'{self.currency} {self.amount:.2f}'
