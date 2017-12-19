import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { CircleMySuffix } from './circle-my-suffix.model';
import { CircleMySuffixPopupService } from './circle-my-suffix-popup.service';
import { CircleMySuffixService } from './circle-my-suffix.service';

@Component({
    selector: 'jhi-circle-my-suffix-delete-dialog',
    templateUrl: './circle-my-suffix-delete-dialog.component.html'
})
export class CircleMySuffixDeleteDialogComponent {

    circle: CircleMySuffix;

    constructor(
        private circleService: CircleMySuffixService,
        public activeModal: NgbActiveModal,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.circleService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'circleListModification',
                content: 'Deleted an circle'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-circle-my-suffix-delete-popup',
    template: ''
})
export class CircleMySuffixDeletePopupComponent implements OnInit, OnDestroy {

    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private circlePopupService: CircleMySuffixPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.circlePopupService
                .open(CircleMySuffixDeleteDialogComponent as Component, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
