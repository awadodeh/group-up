import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { AddressMySuffixComponent } from './address-my-suffix.component';
import { AddressMySuffixDetailComponent } from './address-my-suffix-detail.component';
import { AddressMySuffixPopupComponent } from './address-my-suffix-dialog.component';
import { AddressMySuffixDeletePopupComponent } from './address-my-suffix-delete-dialog.component';

export const addressRoute: Routes = [
    {
        path: 'address-my-suffix',
        component: AddressMySuffixComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.address.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'address-my-suffix/:id',
        component: AddressMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.address.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const addressPopupRoute: Routes = [
    {
        path: 'address-my-suffix-new',
        component: AddressMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.address.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'address-my-suffix/:id/edit',
        component: AddressMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.address.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'address-my-suffix/:id/delete',
        component: AddressMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.address.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
