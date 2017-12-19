import { BaseEntity } from './../../shared';

export const enum Language {
    'FRENCH',
    'ENGLISH',
    'SPANISH'
}

export class EnrollmentHistoryMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public language?: Language,
        public circleId?: number,
        public personId?: number,
    ) {
    }
}
