from models.currencyamount import CurrencyAmount
from models.unit import Unit
from util.check import required


class Product:
    def __init__(self, id: int, name: str, price: CurrencyAmount, unit: Unit):
        self.id = required('id', id)
        self.name = required('name', name)
        self.price = required('price', price)
        self.unit = required('unit', unit)

    def __str__(self):
        return f'{self.name} (id={self.id}, price= {self.price}/{self.unit})'
