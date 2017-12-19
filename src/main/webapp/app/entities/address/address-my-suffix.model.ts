import { BaseEntity } from './../../shared';

export class AddressMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public streetAddress?: string,
        public postalCode?: string,
        public city?: string,
        public stateProvince?: string,
    ) {
    }
}
