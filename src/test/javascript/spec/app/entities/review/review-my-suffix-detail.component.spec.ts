/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { BlogTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { ReviewMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/review/review-my-suffix-detail.component';
import { ReviewMySuffixService } from '../../../../../../main/webapp/app/entities/review/review-my-suffix.service';
import { ReviewMySuffix } from '../../../../../../main/webapp/app/entities/review/review-my-suffix.model';

describe('Component Tests', () => {

    describe('ReviewMySuffix Management Detail Component', () => {
        let comp: ReviewMySuffixDetailComponent;
        let fixture: ComponentFixture<ReviewMySuffixDetailComponent>;
        let service: ReviewMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlogTestModule],
                declarations: [ReviewMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    ReviewMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(ReviewMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(ReviewMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ReviewMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new ReviewMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.review).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
