import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { LayoutComponent } from './components/layout/layout.component';
import { LoginComponent } from './components/login/login/login.component';
import { RegisterComponent } from './components/register/register/register.component';
import { PlaceSearchComponent } from './components/place/place-search/place-search/place-search.component';
import { PlaceViewComponent } from './components/place/place-view/place-view.component';
import { EventSearchComponent } from './components/event/event-search/event-search/event-search.component';
import { EventViewComponent } from './components/event/event-view/event-view/event-view.component';

const routes: Routes = [
  { path: '', redirectTo: 'home', pathMatch: 'full' },
  { path: 'home', component: LayoutComponent },
  { path: 'login', component: LoginComponent },
  { path: 'register', component: RegisterComponent },
  { path: 'places', component: PlaceSearchComponent },
  { path: 'events', component: EventSearchComponent },
  { path: 'place-view/:id', component: PlaceViewComponent },
  { path: 'place-view-admin', component: PlaceViewComponent },
  { path: 'event-view/:id', component: EventViewComponent },
  { path: 'event-view-admin', component: EventViewComponent },
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
