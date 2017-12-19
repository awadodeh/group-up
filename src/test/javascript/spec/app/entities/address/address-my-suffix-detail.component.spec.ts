/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { BlogTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AddressMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/address/address-my-suffix-detail.component';
import { AddressMySuffixService } from '../../../../../../main/webapp/app/entities/address/address-my-suffix.service';
import { AddressMySuffix } from '../../../../../../main/webapp/app/entities/address/address-my-suffix.model';

describe('Component Tests', () => {

    describe('AddressMySuffix Management Detail Component', () => {
        let comp: AddressMySuffixDetailComponent;
        let fixture: ComponentFixture<AddressMySuffixDetailComponent>;
        let service: AddressMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlogTestModule],
                declarations: [AddressMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AddressMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(AddressMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AddressMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AddressMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new AddressMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.address).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
