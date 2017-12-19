/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { BlogTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PersonMySuffixDetailComponent } from '../../../../../../main/webapp/app/entities/person/person-my-suffix-detail.component';
import { PersonMySuffixService } from '../../../../../../main/webapp/app/entities/person/person-my-suffix.service';
import { PersonMySuffix } from '../../../../../../main/webapp/app/entities/person/person-my-suffix.model';

describe('Component Tests', () => {

    describe('PersonMySuffix Management Detail Component', () => {
        let comp: PersonMySuffixDetailComponent;
        let fixture: ComponentFixture<PersonMySuffixDetailComponent>;
        let service: PersonMySuffixService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [BlogTestModule],
                declarations: [PersonMySuffixDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PersonMySuffixService,
                    JhiEventManager
                ]
            }).overrideTemplate(PersonMySuffixDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonMySuffixDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonMySuffixService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new PersonMySuffix(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.person).toEqual(jasmine.objectContaining({id: 10}));
            });
        });
    });

});
