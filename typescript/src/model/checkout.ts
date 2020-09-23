import {CurrencyAmount} from './currencyAmount'
import {ShoppingCart} from './shoppingCart'
import {ExchangeRateService, BASE_CURRENCY} from '../service/exchangeRateService'
import {Check} from '../util/check'

export class Checkout {

    private localCurrency: string = BASE_CURRENCY;

    constructor(private exchangeRateService: ExchangeRateService) {
        Check.required('exchangeRateService', exchangeRateService);
    }

    checkout(shoppingCart: ShoppingCart): void {
        Check.required('shoppingCart', shoppingCart);
        let total = 0;
        for (const product of shoppingCart.items.keys()) {
            const amount = shoppingCart.items.get(product);
            const price = new CurrencyAmount(product.price.currency, amount * product.price.amount);
            const priceConverted = this.exchangeRateService.convert(price, this.localCurrency);
            console.log(`${amount} ${product.unit} ${product} = ${priceConverted}`);
            total += priceConverted.amount;
        }
        console.log("Total: " + new CurrencyAmount(this.localCurrency, total));
    }
}