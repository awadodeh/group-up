import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { EnrollmentHistoryMySuffix } from './enrollment-history-my-suffix.model';
import { EnrollmentHistoryMySuffixService } from './enrollment-history-my-suffix.service';

@Component({
    selector: 'jhi-enrollment-history-my-suffix-detail',
    templateUrl: './enrollment-history-my-suffix-detail.component.html'
})
export class EnrollmentHistoryMySuffixDetailComponent implements OnInit, OnDestroy {

    enrollmentHistory: EnrollmentHistoryMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private enrollmentHistoryService: EnrollmentHistoryMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEnrollmentHistories();
    }

    load(id) {
        this.enrollmentHistoryService.find(id).subscribe((enrollmentHistory) => {
            this.enrollmentHistory = enrollmentHistory;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEnrollmentHistories() {
        this.eventSubscriber = this.eventManager.subscribe(
            'enrollmentHistoryListModification',
            (response) => this.load(this.enrollmentHistory.id)
        );
    }
}
