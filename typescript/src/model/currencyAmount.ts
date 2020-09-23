import {Check} from '../util/check'

export class CurrencyAmount {

    constructor(readonly currency: string, readonly amount: number) {
        Check.required("currency", currency, Check.format(/[A-Z]{3}/, 'must be a currency code'));
        Check.required("amount", amount);
    }

    public toString(): string {
        return `${this.currency} ${this.amount.toFixed(2)}`;
    }
}