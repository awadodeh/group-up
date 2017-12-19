import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager, JhiParseLinks, JhiPaginationUtil, JhiLanguageService, JhiAlertService } from 'ng-jhipster';

import { EnrollmentsMySuffix } from './enrollments-my-suffix.model';
import { EnrollmentsMySuffixService } from './enrollments-my-suffix.service';
import { ITEMS_PER_PAGE, Principal, ResponseWrapper } from '../../shared';
import { PaginationConfig } from '../../blocks/config/uib-pagination.config';

@Component({
    selector: 'jhi-enrollments-my-suffix',
    templateUrl: './enrollments-my-suffix.component.html'
})
export class EnrollmentsMySuffixComponent implements OnInit, OnDestroy {
enrollments: EnrollmentsMySuffix[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        private enrollmentsService: EnrollmentsMySuffixService,
        private jhiAlertService: JhiAlertService,
        private eventManager: JhiEventManager,
        private activatedRoute: ActivatedRoute,
        private principal: Principal
    ) {
        this.currentSearch = activatedRoute.snapshot.params['search'] ? activatedRoute.snapshot.params['search'] : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.enrollmentsService.search({
                query: this.currentSearch,
                }).subscribe(
                    (res: ResponseWrapper) => this.enrollments = res.json,
                    (res: ResponseWrapper) => this.onError(res.json)
                );
            return;
       }
        this.enrollmentsService.query().subscribe(
            (res: ResponseWrapper) => {
                this.enrollments = res.json;
                this.currentSearch = '';
            },
            (res: ResponseWrapper) => this.onError(res.json)
        );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }
    ngOnInit() {
        this.loadAll();
        this.principal.identity().then((account) => {
            this.currentAccount = account;
        });
        this.registerChangeInEnrollments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: EnrollmentsMySuffix) {
        return item.id;
    }
    registerChangeInEnrollments() {
        this.eventSubscriber = this.eventManager.subscribe('enrollmentsListModification', (response) => this.loadAll());
    }

    private onError(error) {
        this.jhiAlertService.error(error.message, null, null);
    }
}
