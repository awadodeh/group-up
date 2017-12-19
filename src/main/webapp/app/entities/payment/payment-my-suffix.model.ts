import { BaseEntity } from './../../shared';

export class PaymentMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public paymentNo?: number,
        public dueDate?: any,
        public amount?: number,
        public personId?: number,
    ) {
    }
}
