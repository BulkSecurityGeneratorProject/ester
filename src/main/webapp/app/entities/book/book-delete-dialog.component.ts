import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiAlertService, JhiEventManager } from 'ng-jhipster';

import { Book } from './book.model';
import { BookPopupService } from './book-popup.service';
import { BookService } from './book.service';

@Component({
    selector: 'jhi-book-delete-dialog',
    templateUrl: './book-delete-dialog.component.html'
})
export class BookDeleteDialogComponent {

    book: Book;

    constructor(
        private bookService: BookService,
        public activeModal: NgbActiveModal,
        private alertService: JhiAlertService,
        private eventManager: JhiEventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bookService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'bookListModification',
                content: 'Deleted an book'
            });
            this.activeModal.dismiss(true);
        });
        this.alertService.success('esterApp.book.deleted', { param : id }, null);
    }
}

@Component({
    selector: 'jhi-book-delete-popup',
    template: ''
})
export class BookDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private bookPopupService: BookPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.bookPopupService
                .open(BookDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
