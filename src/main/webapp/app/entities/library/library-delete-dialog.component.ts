import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Library } from './library.model';
import { LibraryPopupService } from './library-popup.service';
import { LibraryService } from './library.service';

@Component({
    selector: 'jhi-library-delete-dialog',
    templateUrl: './library-delete-dialog.component.html'
})
export class LibraryDeleteDialogComponent {

    library: Library;

    constructor(
        private libraryService: LibraryService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.libraryService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'libraryListModification',
                content: 'Deleted an library'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('esterApp.library.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-library-delete-popup',
    template: ''
})
export class LibraryDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private libraryPopupService: LibraryPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.libraryPopupService
                .open(LibraryDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
