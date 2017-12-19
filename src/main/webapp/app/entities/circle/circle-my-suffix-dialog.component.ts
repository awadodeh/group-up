import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { CircleMySuffix } from './circle-my-suffix.model';
import { CircleMySuffixPopupService } from './circle-my-suffix-popup.service';
import { CircleMySuffixService } from './circle-my-suffix.service';

@Component({
    selector: 'jhi-circle-my-suffix-dialog',
    templateUrl: './circle-my-suffix-dialog.component.html'
})
export class CircleMySuffixDialogComponent implements OnInit {

    circle: CircleMySuffix;
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private jhiAlertService: JhiAlertService,
        private circleService: CircleMySuffixService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.circle.id !== undefined) {
            this.subscribeToSaveResponse(
                this.circleService.update(this.circle));
        } else {
            this.subscribeToSaveResponse(
                this.circleService.create(this.circle));
        }
    }

    private subscribeToSaveResponse(result: Observable<CircleMySuffix>) {
        result.subscribe((res: CircleMySuffix) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError());
    }

    private onSaveSuccess(result: CircleMySuffix) {
        this.eventManager.broadcast({ name: 'circleListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError() {
        this.isSaving = false;
    }

    private onError(error: any) {
        this.jhiAlertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-circle-my-suffix-popup',
    template: ''
})
export class CircleMySuffixPopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private circlePopupService: CircleMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.circlePopupService
                    .open(CircleMySuffixDialogComponent as Component, params['id']);
            } else {
                this.circlePopupService
                    .open(CircleMySuffixDialogComponent as Component);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
