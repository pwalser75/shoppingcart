import {Product} from './product'
import {Check} from '../util/check'

export class ShoppingCart {

    readonly items = new Map<Product, number>();

    public add(amount: number, product: Product): ShoppingCart {
        Check.required('amount', amount, Check.min(0));
        Check.required('product', product);

        let existingAmount = this.items.get(product)
        if (!existingAmount) {
            existingAmount = 0;
        }
        this.items.set(product, existingAmount + amount);
        return this;
    }

    public remove(product: Product): ShoppingCart {
        this.items.delete(product);
        return this;
    }

   public toString(): string {
        return `${this.items.size} items in the shopping cart`;
    }
}