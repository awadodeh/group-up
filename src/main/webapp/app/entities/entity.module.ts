import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { BlogBlogModule } from './blog/blog.module';
import { BlogEntryModule } from './entry/entry.module';
import { BlogTagModule } from './tag/tag.module';
import { BlogCircleMySuffixModule } from './circle/circle-my-suffix.module';
import { BlogPersonMySuffixModule } from './person/person-my-suffix.module';
import { BlogEnrollmentsMySuffixModule } from './enrollments/enrollments-my-suffix.module';
import { BlogEnrollmentHistoryMySuffixModule } from './enrollment-history/enrollment-history-my-suffix.module';
import { BlogAddressMySuffixModule } from './address/address-my-suffix.module';
import { BlogReviewMySuffixModule } from './review/review-my-suffix.module';
import { BlogPaymentMySuffixModule } from './payment/payment-my-suffix.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        BlogBlogModule,
        BlogEntryModule,
        BlogTagModule,
        BlogCircleMySuffixModule,
        BlogPersonMySuffixModule,
        BlogEnrollmentsMySuffixModule,
        BlogEnrollmentHistoryMySuffixModule,
        BlogAddressMySuffixModule,
        BlogReviewMySuffixModule,
        BlogPaymentMySuffixModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class BlogEntityModule {}
