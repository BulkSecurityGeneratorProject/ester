import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { JhiPaginationUtil } from 'ng-jhipster';

import { LibraryComponent } from './library.component';
import { LibraryDetailComponent } from './library-detail.component';
import { LibraryPopupComponent } from './library-dialog.component';
import { LibraryDeletePopupComponent } from './library-delete-dialog.component';

import { Principal } from '../../shared';

export const libraryRoute: Routes = [
    {
        path: 'library',
        component: LibraryComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'esterApp.library.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'library/:id',
        component: LibraryDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'esterApp.library.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const libraryPopupRoute: Routes = [
    {
        path: 'library-new',
        component: LibraryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'esterApp.library.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'library/:id/edit',
        component: LibraryPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'esterApp.library.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'library/:id/delete',
        component: LibraryDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'esterApp.library.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
