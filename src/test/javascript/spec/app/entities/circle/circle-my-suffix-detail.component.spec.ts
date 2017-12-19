/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { BlogTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { CircleMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/circle/circle-my-suffix-detail.component';
import { CircleMySuffixService } from '../../../../../../main/webapp/app/entities/circle/circle-my-suffix.service';
import { CircleMySuffix } from '../../../../../../main/webapp/app/entities/circle/circle-my-suffix.model';

describe('Component Tests', () => {

    describe('CircleMySuffix Management Detail Component', () => {
        let comp: CircleMySuffixDetailComponent;
        let fixture: ComponentFixture<CircleMySuffixDetailComponent>;
        let service: CircleMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlogTestModule],
                declarations: [CircleMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    CircleMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(CircleMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(CircleMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CircleMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new CircleMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.circle).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
