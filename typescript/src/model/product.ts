import {Entity} from './entity'
import {CurrencyAmount} from './currency.amount'
import {Unit} from './unit'
import {Check} from '../util/check'

export class Product extends Entity<number>{

    constructor(id: number, private name: string, private price: CurrencyAmount, private unit: Unit) {
        super('Product', id);
        Check.required('id', id);
        Check.required('name', name);
        Check.required('price', price);
        Check.required('unit', unit);
    }

    toString(): string {
        return `${this.name} (id=${this.id}, price=${this.price}/${this.unit})`;
    }
}
