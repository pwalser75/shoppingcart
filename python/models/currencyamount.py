from util.check import required, has_format, is_number, minimum


class CurrencyAmount:
    def __init__(self, currency, amount):
        self.currency = required('currency', currency, has_format('^[A-Z]{3}$', 'must be an ISO currency code'))
        self.amount = required('amount', amount, is_number(), minimum(0))

    def __str__(self):
        return f'{self.currency} {self.amount:.2f}'
