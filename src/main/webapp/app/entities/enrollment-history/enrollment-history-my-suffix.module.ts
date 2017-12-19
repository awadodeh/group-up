import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlogSharedModule } from '../../shared';
import {
    EnrollmentHistoryMySuffixService,
    EnrollmentHistoryMySuffixPopupService,
    EnrollmentHistoryMySuffixComponent,
    EnrollmentHistoryMySuffixDetailComponent,
    EnrollmentHistoryMySuffixDialogComponent,
    EnrollmentHistoryMySuffixPopupComponent,
    EnrollmentHistoryMySuffixDeletePopupComponent,
    EnrollmentHistoryMySuffixDeleteDialogComponent,
    enrollmentHistoryRoute,
    enrollmentHistoryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...enrollmentHistoryRoute,
    ...enrollmentHistoryPopupRoute,
];

@NgModule({
    imports: [
        BlogSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EnrollmentHistoryMySuffixComponent,
        EnrollmentHistoryMySuffixDetailComponent,
        EnrollmentHistoryMySuffixDialogComponent,
        EnrollmentHistoryMySuffixDeleteDialogComponent,
        EnrollmentHistoryMySuffixPopupComponent,
        EnrollmentHistoryMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        EnrollmentHistoryMySuffixComponent,
        EnrollmentHistoryMySuffixDialogComponent,
        EnrollmentHistoryMySuffixPopupComponent,
        EnrollmentHistoryMySuffixDeleteDialogComponent,
        EnrollmentHistoryMySuffixDeletePopupComponent,
    ],
    providers: [
        EnrollmentHistoryMySuffixService,
        EnrollmentHistoryMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlogEnrollmentHistoryMySuffixModule {}
