import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { JhiDateUtils, JhiDataUtils, JhiEventManager } from 'ng-jhipster';
import { EsterTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { LibraryDetailComponent } from '../../../../../../main/webapp/app/entities/library/library-detail.component';
import { LibraryService } from '../../../../../../main/webapp/app/entities/library/library.service';
import { Library } from '../../../../../../main/webapp/app/entities/library/library.model';

describe('Component Tests', () => {

    describe('Library Management Detail Component', () => {
        let comp: LibraryDetailComponent;
        let fixture: ComponentFixture<LibraryDetailComponent>;
        let service: LibraryService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [EsterTestModule],
                declarations: [LibraryDetailComponent],
                providers: [
                    JhiDateUtils,
                    JhiDataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    LibraryService,
                    JhiEventManager
                ]
            }).overrideTemplate(LibraryDetailComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LibraryDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LibraryService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Library(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.library).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
