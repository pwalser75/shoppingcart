import {Entity} from './entity'
import {Unit} from './unit'
import {CurrencyAmount} from './currencyAmount'
import {Check} from '../util/check'

export class Product extends Entity<number>{

    constructor(id: number, public name: string, public price: CurrencyAmount, public unit: Unit) {
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
