/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { BlogTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EnrollmentHistoryMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/enrollment-history/enrollment-history-my-suffix-detail.component';
import { EnrollmentHistoryMySuffixService } from '../../../../../../main/webapp/app/entities/enrollment-history/enrollment-history-my-suffix.service';
import { EnrollmentHistoryMySuffix } from '../../../../../../main/webapp/app/entities/enrollment-history/enrollment-history-my-suffix.model';

describe('Component Tests', () => {

    describe('EnrollmentHistoryMySuffix Management Detail Component', () => {
        let comp: EnrollmentHistoryMySuffixDetailComponent;
        let fixture: ComponentFixture<EnrollmentHistoryMySuffixDetailComponent>;
        let service: EnrollmentHistoryMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlogTestModule],
                declarations: [EnrollmentHistoryMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EnrollmentHistoryMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(EnrollmentHistoryMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EnrollmentHistoryMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnrollmentHistoryMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new EnrollmentHistoryMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.enrollmentHistory).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
