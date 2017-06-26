import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { JhiEventManager  } from 'ng-jhipster';

import { Library } from './library.model';
import { LibraryService } from './library.service';

@Component({
    selector: 'jhi-library-detail',
    templateUrl: './library-detail.component.html'
})
export class LibraryDetailComponent implements OnInit, OnDestroy {

    library: Library;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: JhiEventManager,
        private libraryService: LibraryService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInLibraries();
    }

    load(id) {
        this.libraryService.find(id).subscribe((library) => {
            this.library = library;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInLibraries() {
        this.eventSubscriber = this.eventManager.subscribe(
            'libraryListModification',
            (response) => this.load(this.library.id)
        );
    }
}
