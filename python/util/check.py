import re


def required(property_name, value, *validators):
    if value is None:
        raise Exception(f'\'{property_name}\' is required')
    if isinstance(value, str) and len(value) == 0:
        raise Exception(f'\'{property_name}\' must not be empty')
    for validator in validators:
        error = validator(value)
        if error:
            raise Exception(f'\'${property_name}\': \'${value}\' ${error}')
    return value


def check(value, condition, error_message):
    if not condition(value):
        raise Exception(error_message)


def is_number():
    return lambda value: check(value, lambda v: isinstance(value, int) or isinstance(value, float), 'must be a number')


def minimum(min_value):
    return lambda value: check(value, lambda v: v >= min_value, f'must be at least {min_value}')


def has_format(regex, error_message):
    return lambda value: check(value, lambda v: re.match(regex, v), error_message)
