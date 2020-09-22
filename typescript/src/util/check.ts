export interface Validator {
    (value: any): string
}

export class Check {

    static required(property: string, value: any, ...validators: Validator[]) {
        if (value == null || value == undefined) {
            throw new Error(`${property} is required`);
        }
        if (typeof value == 'string' && value.length == 0) {
            throw new Error(`${property} must not be empty`);
        }
        for (var validator of validators) {
            var error = validator(value);
            if (error) {
                throw new Error(`${property}: '${value}' ${error}`);
            }
        }
        return value;
    }

    static min(min: number): Validator {
        return value => (value < min) ? `must be at least ${min}` : null;
    }

    static format(regex: RegExp, error: string = 'invalid format'): Validator {
        return value => !value.match(regex) ? error : null;
    }
}