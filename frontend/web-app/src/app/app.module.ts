import { NgModule } from '@angular/core';
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

@NgModule({
  declarations: [
    AppComponent,
    LayoutComponent,
    NavbarComponent,
    LoginComponent,
    RegisterComponent,
    PlaceSearchComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AngularMaterialModule,
    BrowserAnimationsModule,
    HttpClientModule,
    NgxWebstorageModule.forRoot()
  ],
  providers: [UserControllerService, AccountControllerService, AdminControllerService, CulturaAPIService,
    { provide: MatPaginatorIntl, useValue: getDutchPaginatorIntl() }],
  bootstrap: [AppComponent]
})
export class AppModule { }
