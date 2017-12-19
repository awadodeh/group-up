import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlogSharedModule } from '../../shared';
import {
    PaymentMySuffixService,
    PaymentMySuffixPopupService,
    PaymentMySuffixComponent,
    PaymentMySuffixDetailComponent,
    PaymentMySuffixDialogComponent,
    PaymentMySuffixPopupComponent,
    PaymentMySuffixDeletePopupComponent,
    PaymentMySuffixDeleteDialogComponent,
    paymentRoute,
    paymentPopupRoute,
} from './';

const ENTITY_STATES = [
    ...paymentRoute,
    ...paymentPopupRoute,
];

@NgModule({
    imports: [
        BlogSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PaymentMySuffixComponent,
        PaymentMySuffixDetailComponent,
        PaymentMySuffixDialogComponent,
        PaymentMySuffixDeleteDialogComponent,
        PaymentMySuffixPopupComponent,
        PaymentMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        PaymentMySuffixComponent,
        PaymentMySuffixDialogComponent,
        PaymentMySuffixPopupComponent,
        PaymentMySuffixDeleteDialogComponent,
        PaymentMySuffixDeletePopupComponent,
    ],
    providers: [
        PaymentMySuffixService,
        PaymentMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlogPaymentMySuffixModule {}
