import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlogSharedModule } from '../../shared';
import {
    CircleMySuffixService,
    CircleMySuffixPopupService,
    CircleMySuffixComponent,
    CircleMySuffixDetailComponent,
    CircleMySuffixDialogComponent,
    CircleMySuffixPopupComponent,
    CircleMySuffixDeletePopupComponent,
    CircleMySuffixDeleteDialogComponent,
    circleRoute,
    circlePopupRoute,
    CircleMySuffixResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...circleRoute,
    ...circlePopupRoute,
];

@NgModule({
    imports: [
        BlogSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        CircleMySuffixComponent,
        CircleMySuffixDetailComponent,
        CircleMySuffixDialogComponent,
        CircleMySuffixDeleteDialogComponent,
        CircleMySuffixPopupComponent,
        CircleMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        CircleMySuffixComponent,
        CircleMySuffixDialogComponent,
        CircleMySuffixPopupComponent,
        CircleMySuffixDeleteDialogComponent,
        CircleMySuffixDeletePopupComponent,
    ],
    providers: [
        CircleMySuffixService,
        CircleMySuffixPopupService,
        CircleMySuffixResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlogCircleMySuffixModule {}
