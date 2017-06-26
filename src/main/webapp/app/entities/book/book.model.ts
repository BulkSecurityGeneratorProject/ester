import { BaseEntity } from './../../shared';

export class Book implements BaseEntity {
    constructor(
        public id?: number,
        public isbn?: string,
        public name?: string,
        public author?: string,
        public year?: number,
        public coverContentType?: string,
        public cover?: any,
        public active?: boolean,
        public userId?: number,
        public libraries?: BaseEntity[],
    ) {
        this.active = false;
    }
}
