import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager } from 'ng-jhipster';

import { CircleMySuffix } from './circle-my-suffix.model';
import { CircleMySuffixService } from './circle-my-suffix.service';

@Component({
    selector: 'jhi-circle-my-suffix-detail',
    templateUrl: './circle-my-suffix-detail.component.html'
})
export class CircleMySuffixDetailComponent implements OnInit, OnDestroy {

    circle: CircleMySuffix;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private circleService: CircleMySuffixService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInCircles();
    }

    load(id) {
        this.circleService.find(id).subscribe((circle) => {
            this.circle = circle;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInCircles() {
        this.eventSubscriber = this.eventManager.subscribe(
            'circleListModification',
            (response) => this.load(this.circle.id)
        );
    }
}
