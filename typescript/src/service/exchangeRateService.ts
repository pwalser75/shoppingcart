import {CurrencyAmount} from '../model/currencyAmount'

export const BASE_CURRENCY: string = 'CHF'

export interface ExchangeRateService {
     convert(currencyAmount: CurrencyAmount, toCurrency: string): CurrencyAmount;
}

export class ExchangeRateServiceImpl implements ExchangeRateService {

    private readonly baseCurrency: string = 'CHF';
    private readonly exchangeRates: Map<string, number> = new Map<string, number>([
        ['EUR', 1.0744],
        ['USD', 0.9103],
        ['GBP', 1.1794]
    ]);

    convert(currencyAmount: CurrencyAmount, toCurrency: string): CurrencyAmount {
        return new CurrencyAmount(toCurrency,
            currencyAmount.amount * this.exchangeRate(currencyAmount.currency, toCurrency));
    }

   private exchangeRate(fromCurrency: string, toCurrency: string): number {
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

   private requireExchangeRate(currency: string) {
        const exchangeRate = this.exchangeRates.get(currency);
        if (!exchangeRate) {
            throw new Error(`unsupported currency: ${currency}`);
        }
        return exchangeRate;
    }
}