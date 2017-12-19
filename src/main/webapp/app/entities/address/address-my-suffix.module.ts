import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { BlogSharedModule } from '../../shared';
import {
    AddressMySuffixService,
    AddressMySuffixPopupService,
    AddressMySuffixComponent,
    AddressMySuffixDetailComponent,
    AddressMySuffixDialogComponent,
    AddressMySuffixPopupComponent,
    AddressMySuffixDeletePopupComponent,
    AddressMySuffixDeleteDialogComponent,
    addressRoute,
    addressPopupRoute,
} from './';

const ENTITY_STATES = [
    ...addressRoute,
    ...addressPopupRoute,
];

@NgModule({
    imports: [
        BlogSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AddressMySuffixComponent,
        AddressMySuffixDetailComponent,
        AddressMySuffixDialogComponent,
        AddressMySuffixDeleteDialogComponent,
        AddressMySuffixPopupComponent,
        AddressMySuffixDeletePopupComponent,
    ],
    entryComponents: [
        AddressMySuffixComponent,
        AddressMySuffixDialogComponent,
        AddressMySuffixPopupComponent,
        AddressMySuffixDeleteDialogComponent,
        AddressMySuffixDeletePopupComponent,
    ],
    providers: [
        AddressMySuffixService,
        AddressMySuffixPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlogAddressMySuffixModule {}
