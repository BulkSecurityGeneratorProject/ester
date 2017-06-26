import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { EsterSharedModule } from '../../shared';
import {
    LibraryService,
    LibraryPopupService,
    LibraryComponent,
    LibraryDetailComponent,
    LibraryDialogComponent,
    LibraryPopupComponent,
    LibraryDeletePopupComponent,
    LibraryDeleteDialogComponent,
    libraryRoute,
    libraryPopupRoute,
} from './';

const ENTITY_STATES = [
    ...libraryRoute,
    ...libraryPopupRoute,
];

@NgModule({
    imports: [
        EsterSharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        LibraryComponent,
        LibraryDetailComponent,
        LibraryDialogComponent,
        LibraryDeleteDialogComponent,
        LibraryPopupComponent,
        LibraryDeletePopupComponent,
    ],
    entryComponents: [
        LibraryComponent,
        LibraryDialogComponent,
        LibraryPopupComponent,
        LibraryDeleteDialogComponent,
        LibraryDeletePopupComponent,
    ],
    providers: [
        LibraryService,
        LibraryPopupService,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class EsterLibraryModule {}
