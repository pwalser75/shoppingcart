from enum import Enum


class Unit(Enum):
    QUANTITY = 'x'
    KILOGRAM = 'kg'
    LITER = 'l'

    def __str__(self):
        return self.value
