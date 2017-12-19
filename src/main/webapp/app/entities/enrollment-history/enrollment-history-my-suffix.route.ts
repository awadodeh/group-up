import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { EnrollmentHistoryMySuffixComponent } from './enrollment-history-my-suffix.component';
import { EnrollmentHistoryMySuffixDetailComponent } from './enrollment-history-my-suffix-detail.component';
import { EnrollmentHistoryMySuffixPopupComponent } from './enrollment-history-my-suffix-dialog.component';
import { EnrollmentHistoryMySuffixDeletePopupComponent } from './enrollment-history-my-suffix-delete-dialog.component';

export const enrollmentHistoryRoute: Routes = [
    {
        path: 'enrollment-history-my-suffix',
        component: EnrollmentHistoryMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.enrollmentHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'enrollment-history-my-suffix/:id',
        component: EnrollmentHistoryMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.enrollmentHistory.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const enrollmentHistoryPopupRoute: Routes = [
    {
        path: 'enrollment-history-my-suffix-new',
        component: EnrollmentHistoryMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.enrollmentHistory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'enrollment-history-my-suffix/:id/edit',
        component: EnrollmentHistoryMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.enrollmentHistory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'enrollment-history-my-suffix/:id/delete',
        component: EnrollmentHistoryMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.enrollmentHistory.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
