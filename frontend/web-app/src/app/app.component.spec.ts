import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { AppComponent } from './app.component';
import { NavbarComponent } from './components/navbar/navbar.component';
import { LocalStorageService } from 'ngx-webstorage';

describe('AppComponent', () => {
  let fixture: ComponentFixture<AppComponent>;
  let localStorageService: LocalStorageService;
  let component: AppComponent;
  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [
        RouterTestingModule
      ],
      declarations: [
        AppComponent,
        NavbarComponent 
      ],
      providers: [
        { provide: LocalStorageService, useValue: {
          retrieve: jasmine.createSpy('retrieve').and.returnValue('login'),
          store: jasmine.createSpy('store'),
        } }
      ]
    }).compileComponents();
    fixture = TestBed.createComponent(AppComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the app', () => {
    // @ts-ignore
    expect(component).toBeTruthy();
  });
});
