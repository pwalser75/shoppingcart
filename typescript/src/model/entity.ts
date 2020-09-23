export abstract class Entity<T> {

    constructor(private type: string, public id?: T){}

    public equals(obj: Entity<T>): boolean {
        if (obj === this) {
            return true;
        }
        if (obj == null || obj.type !== this.type) {
            return false;
        }
        return (obj as Entity<T>).id === this.id;
    }
}