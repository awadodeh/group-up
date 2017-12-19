import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { EnrollmentsMySuffix } from './enrollments-my-suffix.model';
import { EnrollmentsMySuffixPopupService } from './enrollments-my-suffix-popup.service';
import { EnrollmentsMySuffixService } from './enrollments-my-suffix.service';

@Component({
    selector: 'jhi-enrollments-my-suffix-delete-dialog',
    templateUrl: './enrollments-my-suffix-delete-dialog.component.html'
})
export class EnrollmentsMySuffixDeleteDialogComponent {

    enrollments: EnrollmentsMySuffix;

    constructor(
        private enrollmentsService: EnrollmentsMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.enrollmentsService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'enrollmentsListModification',
                content: 'Deleted an enrollments'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-enrollments-my-suffix-delete-popup',
    template: ''
})
export class EnrollmentsMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private enrollmentsPopupService: EnrollmentsMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.enrollmentsPopupService
                .open(EnrollmentsMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
