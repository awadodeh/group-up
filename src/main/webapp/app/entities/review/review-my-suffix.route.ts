import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { ReviewMySuffixComponent } from './review-my-suffix.component';
import { ReviewMySuffixDetailComponent } from './review-my-suffix-detail.component';
import { ReviewMySuffixPopupComponent } from './review-my-suffix-dialog.component';
import { ReviewMySuffixDeletePopupComponent } from './review-my-suffix-delete-dialog.component';

export const reviewRoute: Routes = [
    {
        path: 'review-my-suffix',
        component: ReviewMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.review.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'review-my-suffix/:id',
        component: ReviewMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.review.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const reviewPopupRoute: Routes = [
    {
        path: 'review-my-suffix-new',
        component: ReviewMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.review.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'review-my-suffix/:id/edit',
        component: ReviewMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.review.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'review-my-suffix/:id/delete',
        component: ReviewMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.review.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
