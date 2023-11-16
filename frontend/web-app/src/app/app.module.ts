import { NgModule } from '@angular/core';
import { FormsModule } from '@angular/forms'
import { BrowserModule } from '@angular/platform-browser';
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
    { provide: MatPaginatorIntl, useValue: getDutchPaginatorIntl() }],
  bootstrap: [AppComponent]
})
export class AppModule { 
  constructor(library: FaIconLibrary) {
    library.addIcons(faStar, faStarHalfAlt, farStar, faTimesCircle);
  }
}
