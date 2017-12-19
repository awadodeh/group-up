import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { EnrollmentHistoryMySuffix } from './enrollment-history-my-suffix.model';
import { EnrollmentHistoryMySuffixPopupService } from './enrollment-history-my-suffix-popup.service';
import { EnrollmentHistoryMySuffixService } from './enrollment-history-my-suffix.service';
import { CircleMySuffix, CircleMySuffixService } from '../circle';
import { PersonMySuffix, PersonMySuffixService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-enrollment-history-my-suffix-dialog',
    templateUrl: './enrollment-history-my-suffix-dialog.component.html'
})
export class EnrollmentHistoryMySuffixDialogComponent implements OnInit {

    enrollmentHistory: EnrollmentHistoryMySuffix;
    isSaving: boolean;

    circles: CircleMySuffix[];

    people: PersonMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private enrollmentHistoryService: EnrollmentHistoryMySuffixService,
        private circleService: CircleMySuffixService,
        private personService: PersonMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.circleService
            .query({filter: 'enrollmenthistory-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.enrollmentHistory.circleId) {
                    this.circles = res.json;
                } else {
                    this.circleService
                        .find(this.enrollmentHistory.circleId)
                        .subscribe((subRes: CircleMySuffix) => {
                            this.circles = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
        this.personService
            .query({filter: 'enrollmenthistory-is-null'})
            .subscribe((res: ResponseWrapper) => {
                if (!this.enrollmentHistory.personId) {
                    this.people = res.json;
                } else {
                    this.personService
                        .find(this.enrollmentHistory.personId)
                        .subscribe((subRes: PersonMySuffix) => {
                            this.people = [subRes].concat(res.json);
                        }, (subRes: ResponseWrapper) => this.onError(subRes.json));
                }
            }, (res: ResponseWrapper) => this.onError(res.json));
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.enrollmentHistory.id !== undefined) {
            this.subscribeToSaveResponse(
                this.enrollmentHistoryService.update(this.enrollmentHistory));
        } else {
            this.subscribeToSaveResponse(
                this.enrollmentHistoryService.create(this.enrollmentHistory));
        }
    }

    private subscribeToSaveResponse(result: Observable<EnrollmentHistoryMySuffix>) {
        result.subscribe((res: EnrollmentHistoryMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: EnrollmentHistoryMySuffix) {
        this.eventManager.broadcast({ name: 'enrollmentHistoryListModification', content: 'OK'});
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
    selector: 'jhi-enrollment-history-my-suffix-popup',
    template: ''
})
export class EnrollmentHistoryMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private enrollmentHistoryPopupService: EnrollmentHistoryMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.enrollmentHistoryPopupService
                    .open(EnrollmentHistoryMySuffixDialogComponent as Component, params['id']);
            } else {
                this.enrollmentHistoryPopupService
                    .open(EnrollmentHistoryMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
