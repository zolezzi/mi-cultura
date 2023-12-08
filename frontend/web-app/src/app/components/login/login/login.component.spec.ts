import { ComponentFixture, TestBed } from '@angular/core/testing';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { RouterTestingModule } from '@angular/router/testing';
import { LoginComponent } from './login.component';
import { JwtResponseDTO, UserControllerService, UserLoginVO } from 'src/app/api';
import { LocalStorageService } from 'ngx-webstorage';
import { of, throwError } from 'rxjs';
import { MatFormFieldModule } from '@angular/material/form-field';
import { HttpResponse } from '@angular/common/http';
import { Router } from '@angular/router';

describe('LoginComponent', () => {
  let component: LoginComponent;
  let fixture: ComponentFixture<LoginComponent>;
  let userService: jasmine.SpyObj<UserControllerService>;
  let localStorageService: jasmine.SpyObj<LocalStorageService>;
  let routerSpy: jasmine.SpyObj<Router>;

  const responseMock : JwtResponseDTO = { token: 'g54gynyn6nnn5erbebereb', firstname: 'Han', lastname: 'Solo', role: 'USER', userId: 123, accountId: 456 };
  const httpResponse: HttpResponse<JwtResponseDTO> = new HttpResponse({ body: responseMock });
  
  beforeEach(async () => {
    const userServiceSpy = jasmine.createSpyObj('UserControllerService', ['login']);
    const localStorageServiceSpy = jasmine.createSpyObj('LocalStorageService', ['store']);
    const router = jasmine.createSpyObj('Router', ['navigate']);
    await TestBed.configureTestingModule({
      declarations: [LoginComponent],
      imports: [        
        ReactiveFormsModule,
        FormsModule,
        MatFormFieldModule, 
        RouterTestingModule],
      providers: [
        { provide: UserControllerService, useValue: userServiceSpy },
        { provide: LocalStorageService, useValue: localStorageServiceSpy }
      ],
    }).compileComponents();

    userService = TestBed.inject(UserControllerService) as jasmine.SpyObj<UserControllerService>;
    localStorageService = TestBed.inject(LocalStorageService) as jasmine.SpyObj<LocalStorageService>;
    routerSpy = TestBed.inject(Router) as jasmine.SpyObj<Router>;
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LoginComponent);
    component = fixture.componentInstance;
    fixture.detectChanges();
  });

  it('should create', () => {
    // @ts-ignore
    expect(component).toBeTruthy();
  });

  it('should create the login form with required fields', () => {
    // @ts-ignore
    expect(component.loginForm).toBeDefined();
    // @ts-ignore
    expect(component.loginForm.controls['email']).toBeDefined();
    // @ts-ignore
    expect(component.loginForm.controls['password']).toBeDefined();
    // @ts-ignore 
    expect(component.loginForm.controls['email'].valid).toBeFalsy();
    // @ts-ignore
    expect(component.loginForm.controls['password'].valid).toBeFalsy();
  });

  it('should mark form fields as invalid if they are empty', () => {
    const emailControl = component.loginForm.controls['email'];
    const passwordControl = component.loginForm.controls['password'];
  
    emailControl.setValue('');
    passwordControl.setValue('');
    // @ts-ignore
    expect(emailControl.valid).toBeFalsy();
    // @ts-ignore
    expect(passwordControl.valid).toBeFalsy();
  });

  it('should set submitted to true when the form is invalid', () => {
    component.onSubmit({ email: '', password: '' });
    // @ts-ignore
    expect(component.submitted).toBeTruthy();
  });

  it('should handle error on login', () => {
    userService.login.and.returnValue(throwError('Some error'));

    const formValues = { email: 'test@example.com', password: 'password123' };
    component.loginForm.setValue({ email: 'test@example.com', password: 'password123' });
    component.onSubmit(formValues);
    // @ts-ignore
    expect(userService.login).toHaveBeenCalledWith(jasmine.objectContaining<UserLoginVO>({ email: formValues.email, password: formValues.password }));
    // @ts-ignore
    expect(localStorageService.store).not.toHaveBeenCalled();
  });

});