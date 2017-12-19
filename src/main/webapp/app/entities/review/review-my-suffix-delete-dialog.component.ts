import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ReviewMySuffix } from './review-my-suffix.model';
import { ReviewMySuffixPopupService } from './review-my-suffix-popup.service';
import { ReviewMySuffixService } from './review-my-suffix.service';

@Component({
    selector: 'jhi-review-my-suffix-delete-dialog',
    templateUrl: './review-my-suffix-delete-dialog.component.html'
})
export class ReviewMySuffixDeleteDialogComponent {

    review: ReviewMySuffix;

    constructor(
        private reviewService: ReviewMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reviewService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'reviewListModification',
                content: 'Deleted an review'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-review-my-suffix-delete-popup',
    template: ''
})
export class ReviewMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private reviewPopupService: ReviewMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.reviewPopupService
                .open(ReviewMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
