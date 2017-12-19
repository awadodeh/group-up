import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EnrollmentHistoryMySuffix } from './enrollment-history-my-suffix.model';
import { EnrollmentHistoryMySuffixService } from './enrollment-history-my-suffix.service';

@Injectable()
export class EnrollmentHistoryMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private modalService: NgbModal,
        private router: Router,
        private enrollmentHistoryService: EnrollmentHistoryMySuffixService

    ) {
        this.ngbModalRef = null;
    }

    open(component: Component, id?: number | any): Promise<NgbModalRef> {
        return new Promise<NgbModalRef>((resolve, reject) => {
            const isOpen = this.ngbModalRef !== null;
            if (isOpen) {
                resolve(this.ngbModalRef);
            }

            if (id) {
                this.enrollmentHistoryService.find(id).subscribe((enrollmentHistory) => {
                    this.ngbModalRef = this.enrollmentHistoryModalRef(component, enrollmentHistory);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.enrollmentHistoryModalRef(component, new EnrollmentHistoryMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    enrollmentHistoryModalRef(component: Component, enrollmentHistory: EnrollmentHistoryMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.enrollmentHistory = enrollmentHistory;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.ngbModalRef = null;
        });
        return modalRef;
    }
}
