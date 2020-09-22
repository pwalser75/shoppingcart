import {Check} from '../util/check'

export class CurrencyAmount {

    constructor(private currency: string, private amount: number) {
        Check.required("currency", currency, Check.format(/[A-Z]{3}/, 'must be a currency code'));
        Check.required("amount", amount);
    }

    public getCurrency(): string {
        return this.currency;
    }

    public getAmount(): number {
        return this.amount;
    }

    public toString(): string {
        return `${this.currency} ${this.amount.toFixed(2)}`;
    }
}