import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService, JhiDataUtils } from 'ng-jhipster';

import { ReviewMySuffix } from './review-my-suffix.model';
import { ReviewMySuffixPopupService } from './review-my-suffix-popup.service';
import { ReviewMySuffixService } from './review-my-suffix.service';
import { PersonMySuffix, PersonMySuffixService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-review-my-suffix-dialog',
    templateUrl: './review-my-suffix-dialog.component.html'
})
export class ReviewMySuffixDialogComponent implements OnInit {

    review: ReviewMySuffix;
    isSaving: boolean;

    people: PersonMySuffix[];

    constructor(
        public activeModal: NgbActiveModal,
        private dataUtils: JhiDataUtils,
        private jhiAlertService: JhiAlertService,
        private reviewService: ReviewMySuffixService,
        private personService: PersonMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }

    byteSize(field) {
        return this.dataUtils.byteSize(field);
    }

    openFile(contentType, field) {
        return this.dataUtils.openFile(contentType, field);
    }

    setFileData(event, entity, field, isImage) {
        this.dataUtils.setFileData(event, entity, field, isImage);
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.review.id !== undefined) {
            this.subscribeToSaveResponse(
                this.reviewService.update(this.review));
        } else {
            this.subscribeToSaveResponse(
                this.reviewService.create(this.review));
        }
    }

    private subscribeToSaveResponse(result: Observable<ReviewMySuffix>) {
        result.subscribe((res: ReviewMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: ReviewMySuffix) {
        this.eventManager.broadcast({ name: 'reviewListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }

    trackPersonById(index: number, item: PersonMySuffix) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-review-my-suffix-popup',
    template: ''
})
export class ReviewMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reviewPopupService: ReviewMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.reviewPopupService
                    .open(ReviewMySuffixDialogComponent as Component, params['id']);
            } else {
                this.reviewPopupService
                    .open(ReviewMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
