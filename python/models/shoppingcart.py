from util.check import required, is_number, minimum


class ShoppingCart:
    def __init__(self):
        self.items = {}

    def add(self, amount, product):
        required('amount', amount, is_number(), minimum(0))
        required('product', product)

        found = [x for x in self.items.keys() if x.id == product.id]
        if len(found) > 0:
            existing = found[0]
            self.items[existing] += amount
        else:
            self.items[product] = amount
        return self

    def remove(self, product):
        for item in [x for x in self.items.keys() if x.id == product.id]:
            del self.items[item]
        return self

    def list(self):
        return self.items

    def __str__(self):
        return f'{len(self.items)} items in the shopping cart'
