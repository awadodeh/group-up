import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { EnrollmentsMySuffixComponent } from './enrollments-my-suffix.component';
import { EnrollmentsMySuffixDetailComponent } from './enrollments-my-suffix-detail.component';
import { EnrollmentsMySuffixPopupComponent } from './enrollments-my-suffix-dialog.component';
import { EnrollmentsMySuffixDeletePopupComponent } from './enrollments-my-suffix-delete-dialog.component';

export const enrollmentsRoute: Routes = [
    {
        path: 'enrollments-my-suffix',
        component: EnrollmentsMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.enrollments.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'enrollments-my-suffix/:id',
        component: EnrollmentsMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.enrollments.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const enrollmentsPopupRoute: Routes = [
    {
        path: 'enrollments-my-suffix-new',
        component: EnrollmentsMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.enrollments.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'enrollments-my-suffix/:id/edit',
        component: EnrollmentsMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.enrollments.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'enrollments-my-suffix/:id/delete',
        component: EnrollmentsMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.enrollments.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
