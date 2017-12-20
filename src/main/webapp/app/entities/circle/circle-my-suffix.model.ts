import { BaseEntity } from './../../shared';

export class CircleMySuffix implements BaseEntity {
    constructor(
        public id?: number,
        public circleName?: string,
        public circleWorth?: number,
        public startDate?: any,
        public endDate?: any,
        public numberOfMembers?: number,
        public enrollments?: BaseEntity[],
        public members?: BaseEntity[],
        public numberOfEnrolledMemebers?: number
    ) {
    }
}
