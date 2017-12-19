import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { EnrollmentsMySuffix } from './enrollments-my-suffix.model';
import { EnrollmentsMySuffixService } from './enrollments-my-suffix.service';

@Component({
    selector: 'jhi-enrollments-my-suffix-detail',
    templateUrl: './enrollments-my-suffix-detail.component.html'
})
export class EnrollmentsMySuffixDetailComponent implements OnInit, OnDestroy {

    enrollments: EnrollmentsMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private enrollmentsService: EnrollmentsMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInEnrollments();
    }

    load(id) {
        this.enrollmentsService.find(id).subscribe((enrollments) => {
            this.enrollments = enrollments;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInEnrollments() {
        this.eventSubscriber = this.eventManager.subscribe(
            'enrollmentsListModification',
            (response) => this.load(this.enrollments.id)
        );
    }
}
