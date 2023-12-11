import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms'
import { BrowserModule } from '@angular/platform-browser';
import { LocationStrategy, HashLocationStrategy } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LayoutComponent } from './components/layout/layout.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { AngularMaterialModule } from './shared/angular-material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { LoginComponent } from './components/login/login/login.component';
import { RegisterComponent } from './components/register/register/register.component';
import { AccountControllerService } from './api/service/accountController.service';
import { AdminControllerService } from './api/service/adminController.service';
import { UserControllerService } from './api/service/userController.service';
import { PlaceSearchComponent } from './components/place/place-search/place-search/place-search.component';
import { getDutchPaginatorIntl } from './components/place/place-search/place-search/dutch-paginator-intl';
import { MatPaginatorIntl } from '@angular/material/paginator';
import { CulturaAPIService } from './shared/service/cultura-api.service';
import { PlaceControllerService } from './api/service/placeController.service';
import { PlaceViewComponent } from './components/place/place-view/place-view.component';
import { ConfirmDialogComponent } from './components/confirm-dialog/confirm-dialog.component';
import { BarRatingModule } from "ngx-bar-rating";
import { FaIconLibrary, FontAwesomeModule } from '@fortawesome/angular-fontawesome';
import { faStar, faStarHalfAlt, faTimesCircle } from '@fortawesome/free-solid-svg-icons';
import { faStar as farStar } from '@fortawesome/free-regular-svg-icons';
import { ReviewControllerService } from './api/service/reviewController.service';
import { EventSearchComponent } from './components/event/event-search/event-search/event-search.component';
import { EventViewComponent } from './components/event/event-view/event-view/event-view.component';
import { EventControllerService } from './api/service/eventController.service';

@NgModule({
  declarations: [
    AppComponent,
    LayoutComponent,
    NavbarComponent,
    LoginComponent,
    RegisterComponent,
    PlaceSearchComponent,
    PlaceViewComponent,
    ConfirmDialogComponent,
    EventSearchComponent,
    EventViewComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AngularMaterialModule,
    BrowserAnimationsModule,
    HttpClientModule,
    FormsModule,
    BarRatingModule,
    FontAwesomeModule,
    NgxWebstorageModule.forRoot()
  ],
  providers: [UserControllerService, AccountControllerService, AdminControllerService, PlaceControllerService, CulturaAPIService, 
    ReviewControllerService, EventControllerService,
    { provide: MatPaginatorIntl, useValue: getDutchPaginatorIntl() },
    { provide: LocationStrategy, useClass: HashLocationStrategy }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { 
  constructor(library: FaIconLibrary) {
    library.addIcons(faStar, faStarHalfAlt, farStar, faTimesCircle);
  }
}
