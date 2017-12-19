/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { BlogTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { EnrollmentsMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/enrollments/enrollments-my-suffix-detail.component';
import { EnrollmentsMySuffixService } from '../../../../../../main/webapp/app/entities/enrollments/enrollments-my-suffix.service';
import { EnrollmentsMySuffix } from '../../../../../../main/webapp/app/entities/enrollments/enrollments-my-suffix.model';

describe('Component Tests', () => {

    describe('EnrollmentsMySuffix Management Detail Component', () => {
        let comp: EnrollmentsMySuffixDetailComponent;
        let fixture: ComponentFixture<EnrollmentsMySuffixDetailComponent>;
        let service: EnrollmentsMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlogTestModule],
                declarations: [EnrollmentsMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    EnrollmentsMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(EnrollmentsMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(EnrollmentsMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(EnrollmentsMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new EnrollmentsMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.enrollments).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
