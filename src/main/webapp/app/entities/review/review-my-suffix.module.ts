import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlogSharedModule } from '../../shared';
import {
    ReviewMySuffixService,
    ReviewMySuffixPopupService,
    ReviewMySuffixComponent,
    ReviewMySuffixDetailComponent,
    ReviewMySuffixDialogComponent,
    ReviewMySuffixPopupComponent,
    ReviewMySuffixDeletePopupComponent,
    ReviewMySuffixDeleteDialogComponent,
    reviewRoute,
    reviewPopupRoute,
} from './';

const ENTITY_STATES = [
    ...reviewRoute,
    ...reviewPopupRoute,
];

@NgModule({
    imports: [
        BlogSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        ReviewMySuffixComponent,
        ReviewMySuffixDetailComponent,
        ReviewMySuffixDialogComponent,
        ReviewMySuffixDeleteDialogComponent,
        ReviewMySuffixPopupComponent,
        ReviewMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        ReviewMySuffixComponent,
        ReviewMySuffixDialogComponent,
        ReviewMySuffixPopupComponent,
        ReviewMySuffixDeleteDialogComponent,
        ReviewMySuffixDeletePopupComponent,
    ],
    providers: [
        ReviewMySuffixService,
        ReviewMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlogReviewMySuffixModule {}
