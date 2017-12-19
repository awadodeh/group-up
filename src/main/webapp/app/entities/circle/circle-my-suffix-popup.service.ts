import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { DatePipe } from '@angular/common';
import { CircleMySuffix } from './circle-my-suffix.model';
import { CircleMySuffixService } from './circle-my-suffix.service';

@Injectable()
export class CircleMySuffixPopupService {
    private ngbModalRef: NgbModalRef;

    constructor(
        private datePipe: DatePipe,
        private modalService: NgbModal,
        private router: Router,
        private circleService: CircleMySuffixService

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
                this.circleService.find(id).subscribe((circle) => {
                    circle.startDate = this.datePipe
                        .transform(circle.startDate, 'yyyy-MM-ddTHH:mm:ss');
                    circle.endDate = this.datePipe
                        .transform(circle.endDate, 'yyyy-MM-ddTHH:mm:ss');
                    this.ngbModalRef = this.circleModalRef(component, circle);
                    resolve(this.ngbModalRef);
                });
            } else {
                // setTimeout used as a workaround for getting ExpressionChangedAfterItHasBeenCheckedError
                setTimeout(() => {
                    this.ngbModalRef = this.circleModalRef(component, new CircleMySuffix());
                    resolve(this.ngbModalRef);
                }, 0);
            }
        });
    }

    circleModalRef(component: Component, circle: CircleMySuffix): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.circle = circle;
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
