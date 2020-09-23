export interface Validator<T> {
    (value: T): string
}

export class Check {

    static required<T> (property: string, value: T, ...validators: Validator<T>[]): T {
        if (value == null || value == undefined) {
            throw new Error(`${property} is required`);
        }
        if (typeof value == 'string' && value.length == 0) {
            throw new Error(`${property} must not be empty`);
        }
        for (const validator of validators) {
            const error = validator(value);
            if (error) {
                throw new Error(`${property}: '${value}' ${error}`);
            }
        }
        return value;
    }

    static min(min: number): Validator<number> {
        return value => (value < min) ? `must be at least ${min}` : null;
    }

    static format(regex: RegExp, error: string = 'invalid format'): Validator<string> {
        return value => !value.match(regex) ? error : null;
    }
}