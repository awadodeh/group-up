import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { CircleMySuffixComponent } from './circle-my-suffix.component';
import { CircleMySuffixDetailComponent } from './circle-my-suffix-detail.component';
import { CircleMySuffixPopupComponent } from './circle-my-suffix-dialog.component';
import { CircleMySuffixDeletePopupComponent } from './circle-my-suffix-delete-dialog.component';

@Injectable()
export class CircleMySuffixResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: JhiPaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const circleRoute: Routes = [
    {
        path: 'circle-my-suffix',
        component: CircleMySuffixComponent,
        resolve: {
            'pagingParams': CircleMySuffixResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.circle.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'circle-my-suffix/:id',
        component: CircleMySuffixDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.circle.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const circlePopupRoute: Routes = [
    {
        path: 'circle-my-suffix-new',
        component: CircleMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.circle.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'circle-my-suffix/:id/edit',
        component: CircleMySuffixPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.circle.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'circle-my-suffix/:id/delete',
        component: CircleMySuffixDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'blogApp.circle.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
