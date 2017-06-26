import { BaseEntity } from './../../shared';

export class Library implements BaseEntity {
    constructor(
        public id?: number,
        public name?: string,
        public address?: string,
        public contact?: string,
    ) {
    }
}
