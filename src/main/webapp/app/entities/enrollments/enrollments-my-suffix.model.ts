import { BaseEntity } from './../../shared';

export class EnrollmentsMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public personId?: number,
        public circleId?: number,
    ) {
    }
}
