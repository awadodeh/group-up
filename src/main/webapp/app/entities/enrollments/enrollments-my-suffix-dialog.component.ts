import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EnrollmentsMySuffix } from './enrollments-my-suffix.model';
import { EnrollmentsMySuffixPopupService } from './enrollments-my-suffix-popup.service';
import { EnrollmentsMySuffixService } from './enrollments-my-suffix.service';
import { CircleMySuffix, CircleMySuffixService } from '../circle';
import { PersonMySuffix, PersonMySuffixService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-enrollments-my-suffix-dialog',
    templateUrl: './enrollments-my-suffix-dialog.component.html'
})
export class EnrollmentsMySuffixDialogComponent implements OnInit {

    enrollments: EnrollmentsMySuffix;
    isSaving: boolean;

    circles: CircleMySuffix[];

    people: PersonMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private enrollmentsService: EnrollmentsMySuffixService,
        private circleService: CircleMySuffixService,
        private personService: PersonMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.circleService.query()
            .subscribe((res: ResponseWrapper) => { this.circles = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.enrollments.id !== undefined) {
            this.subscribeToSaveResponse(
                this.enrollmentsService.update(this.enrollments));
        } else {
            this.subscribeToSaveResponse(
                this.enrollmentsService.create(this.enrollments));
        }
    }

    private subscribeToSaveResponse(result: Observable<EnrollmentsMySuffix>) {
        result.subscribe((res: EnrollmentsMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: EnrollmentsMySuffix) {
        this.eventManager.broadcast({ name: 'enrollmentsListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackCircleById(index: number, item: CircleMySuffix) {
        return item.id;
    }

    trackPersonById(index: number, item: PersonMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-enrollments-my-suffix-popup',
    template: ''
})
export class EnrollmentsMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private enrollmentsPopupService: EnrollmentsMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.enrollmentsPopupService
                    .open(EnrollmentsMySuffixDialogComponent as Component, params['id']);
            } else {
                this.enrollmentsPopupService
                    .open(EnrollmentsMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
