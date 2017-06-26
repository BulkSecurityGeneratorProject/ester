import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { Library } from './library.model';
import { LibraryPopupService } from './library-popup.service';
import { LibraryService } from './library.service';

@Component({
    selector: 'jhi-library-dialog',
    templateUrl: './library-dialog.component.html'
})
export class LibraryDialogComponent implements OnInit {

    library: Library;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private libraryService: LibraryService,
        private eventManager: JhiEventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.library.id !== undefined) {
            this.subscribeToSaveResponse(
                this.libraryService.update(this.library), false);
        } else {
            this.subscribeToSaveResponse(
                this.libraryService.create(this.library), true);
        }
    }

    private subscribeToSaveResponse(result: Observable<Library>, isCreated: boolean) {
        result.subscribe((res: Library) =>
            this.onSaveSuccess(res, isCreated), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Library, isCreated: boolean) {
        this.alertService.success(
            isCreated ? 'esterApp.library.created'
            : 'esterApp.library.updated',
            { param : result.id }, null);

        this.eventManager.broadcast({ name: 'libraryListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-library-popup',
    template: ''
})
export class LibraryPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private libraryPopupService: LibraryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.libraryPopupService
                    .open(LibraryDialogComponent, params['id']);
            } else {
                this.modalRef = this.libraryPopupService
                    .open(LibraryDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
