import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlogSharedModule } from '../../shared';
import {
    PersonMySuffixService,
    PersonMySuffixPopupService,
    PersonMySuffixComponent,
    PersonMySuffixDetailComponent,
    PersonMySuffixDialogComponent,
    PersonMySuffixPopupComponent,
    PersonMySuffixDeletePopupComponent,
    PersonMySuffixDeleteDialogComponent,
    personRoute,
    personPopupRoute,
    PersonMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...personRoute,
    ...personPopupRoute,
];

@NgModule({
    imports: [
        BlogSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PersonMySuffixComponent,
        PersonMySuffixDetailComponent,
        PersonMySuffixDialogComponent,
        PersonMySuffixDeleteDialogComponent,
        PersonMySuffixPopupComponent,
        PersonMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        PersonMySuffixComponent,
        PersonMySuffixDialogComponent,
        PersonMySuffixPopupComponent,
        PersonMySuffixDeleteDialogComponent,
        PersonMySuffixDeletePopupComponent,
    ],
    providers: [
        PersonMySuffixService,
        PersonMySuffixPopupService,
        PersonMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlogPersonMySuffixModule {}
