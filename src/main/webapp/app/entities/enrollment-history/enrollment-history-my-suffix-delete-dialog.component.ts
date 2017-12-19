import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EnrollmentHistoryMySuffix } from './enrollment-history-my-suffix.model';
import { EnrollmentHistoryMySuffixPopupService } from './enrollment-history-my-suffix-popup.service';
import { EnrollmentHistoryMySuffixService } from './enrollment-history-my-suffix.service';

@Component({
    selector: 'jhi-enrollment-history-my-suffix-delete-dialog',
    templateUrl: './enrollment-history-my-suffix-delete-dialog.component.html'
})
export class EnrollmentHistoryMySuffixDeleteDialogComponent {

    enrollmentHistory: EnrollmentHistoryMySuffix;

    constructor(
        private enrollmentHistoryService: EnrollmentHistoryMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.enrollmentHistoryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'enrollmentHistoryListModification',
                content: 'Deleted an enrollmentHistory'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-enrollment-history-my-suffix-delete-popup',
    template: ''
})
export class EnrollmentHistoryMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private enrollmentHistoryPopupService: EnrollmentHistoryMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.enrollmentHistoryPopupService
                .open(EnrollmentHistoryMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
