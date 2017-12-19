import { BaseEntity } from './../../shared';

export class ReviewMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public reviewSummery?: any,
        public personId?: number,
    ) {
    }
}
