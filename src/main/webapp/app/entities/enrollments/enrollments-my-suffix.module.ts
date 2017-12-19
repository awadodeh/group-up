import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlogSharedModule } from '../../shared';
import {
    EnrollmentsMySuffixService,
    EnrollmentsMySuffixPopupService,
    EnrollmentsMySuffixComponent,
    EnrollmentsMySuffixDetailComponent,
    EnrollmentsMySuffixDialogComponent,
    EnrollmentsMySuffixPopupComponent,
    EnrollmentsMySuffixDeletePopupComponent,
    EnrollmentsMySuffixDeleteDialogComponent,
    enrollmentsRoute,
    enrollmentsPopupRoute,
} from './';

const ENTITY_STATES = [
    ...enrollmentsRoute,
    ...enrollmentsPopupRoute,
];

@NgModule({
    imports: [
        BlogSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        EnrollmentsMySuffixComponent,
        EnrollmentsMySuffixDetailComponent,
        EnrollmentsMySuffixDialogComponent,
        EnrollmentsMySuffixDeleteDialogComponent,
        EnrollmentsMySuffixPopupComponent,
        EnrollmentsMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        EnrollmentsMySuffixComponent,
        EnrollmentsMySuffixDialogComponent,
        EnrollmentsMySuffixPopupComponent,
        EnrollmentsMySuffixDeleteDialogComponent,
        EnrollmentsMySuffixDeletePopupComponent,
    ],
    providers: [
        EnrollmentsMySuffixService,
        EnrollmentsMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlogEnrollmentsMySuffixModule {}
