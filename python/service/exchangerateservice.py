from models.currencyamount import CurrencyAmount
from util.check import required, is_currency


class ExchangeRateService:

    def __init__(self):
        self.base_currency = 'CHF'
        self.precision = 2
        self.exchange_rates = {
            'EUR': 1.0744,
            'USD': 0.9103,
            'GBP': 1.1794
        }

    def convert(self, currency_amount: CurrencyAmount, to_currency: str):

        required('currency_amount', currency_amount)
        required('to_currency', to_currency, is_currency())

        return CurrencyAmount(to_currency,
                              round(currency_amount.amount * self.exchange_rate(currency_amount.currency, to_currency),
                                    self.precision))

    def exchange_rate(self, from_currency: str, to_currency: str):
        required('from_currency', from_currency, is_currency())
        required('to_currency', to_currency, is_currency())

        if from_currency == to_currency:
            return 1
        if from_currency == self.base_currency:
            return 1 / self.require_exchange_rate(to_currency)
        if to_currency == self.base_currency:
            return self.require_exchange_rate(from_currency)
        return self.exchange_rate(from_currency, self.base_currency) * \
               self.exchange_rate(self.base_currency, to_currency)

    def require_exchange_rate(self, currency: str):
        required('currency', currency, is_currency())
        if currency not in self.exchange_rates:
            raise Exception(f'unsupported currency: {currency}')
        return self.exchange_rates[currency]
