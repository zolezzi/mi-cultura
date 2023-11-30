import { ComponentFixture, TestBed } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
import { LocalStorageService } from 'ngx-webstorage';
import { NavbarComponent } from './navbar.component';

describe('NavbarComponent', () => {
  let component: NavbarComponent;
  let fixture: ComponentFixture<NavbarComponent>;
  let localStorageService: LocalStorageService;
  let router: Router;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      declarations: [NavbarComponent],
      imports: [RouterTestingModule],
      providers: [
        { provide:LocalStorageService, useValue: {
          retrieve: jasmine.createSpy('retrieve').and.callFake((key: string) => {
            if (key === 'ROLE') {
              return 'ADMIN';
            } else if (key === 'ACCESS_TOKEN') {
              return 'adasdasdasdasdasds';
            } else if (key === 'FULL_NAME') {
              return 'Han Solo';
            }
            return null;
          }),
          clear: jasmine.createSpyObj('LocalStorageService', ['clear'])
        }
        }],
    }).compileComponents();
    localStorageService = TestBed.inject(LocalStorageService);
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(NavbarComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create the component', () => {
    component.ngOnInit();
    expect(component.isLoggedIn).toBe(true);
    expect(component.isAdmin).toBe(true);
    expect(component.fullName).toBe('Han Solo');
  });

  it('should set initial values correctly when user is logged in', () => {
    component.ngOnInit();
    expect(component.isLoggedIn).toBe(true);
    expect(component.isAdmin).toBe(true);
    expect(component.fullName).toBeDefined();
  });

  it('should log out user and navigate to home when logout() is called', () => {
    spyOn(localStorageService, 'clear');
    spyOn(component.getRouter(), 'navigate');

    component.logout();

    expect(localStorageService.clear).toHaveBeenCalledWith('ACCESS_TOKEN');
    expect(component.isLoggedIn).toBe(false);
    expect(component.getRouter().navigate).toHaveBeenCalledWith(['/home']);
  });

});