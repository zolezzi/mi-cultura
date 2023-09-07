import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LayoutComponent } from './components/layout/layout.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { AngularMaterialModule } from './shared/angular-material.module';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { NgxWebstorageModule } from 'ngx-webstorage';
import { LoginComponent } from './components/login/login/login.component';
import { RegisterComponent } from './components/register/register/register.component';


@NgModule({
  declarations: [
    AppComponent,
    LayoutComponent,
    NavbarComponent,
    LoginComponent,
    RegisterComponent,
    
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    AngularMaterialModule,
    BrowserAnimationsModule,
    NgxWebstorageModule.forRoot()
  ],
  providers: [],
  bootstrap: [AppComponent]
})
export class AppModule { }
