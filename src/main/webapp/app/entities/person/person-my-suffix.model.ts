import { BaseEntity } from './../../shared';

export class PersonMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public firstName?: string,
        public lastName?: string,
        public email?: string,
        public phoneNumber?: string,
        public addressId?: number,
        public payments?: BaseEntity[],
        public reviews?: BaseEntity[],
        public enrollments?: BaseEntity[],
    ) {
    }
}
