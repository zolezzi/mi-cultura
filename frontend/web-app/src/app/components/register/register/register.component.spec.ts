import { TestBed, ComponentFixture, fakeAsync, tick } from '@angular/core/testing';
import { RouterTestingModule } from '@angular/router/testing';
import { Router } from '@angular/router';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { of, throwError } from 'rxjs';
import { RegisterComponent } from './register.component';
import { UserControllerService, UserDTO } from 'src/app/api';
import { HttpResponse } from '@angular/common/http';
import { LocalStorageService } from 'ngx-webstorage';
import { MatFormFieldModule } from '@angular/material/form-field';

describe('RegisterComponent', () => {
  let component: RegisterComponent;
  let fixture: ComponentFixture<RegisterComponent>;
  let userService: jasmine.SpyObj<UserControllerService>;
  let localStorageService: LocalStorageService;
  let router: Router;

  const mockUser: UserDTO = {
    id: 1,
    email: 'carlos.cardozo@gmail.com',
    password: 'password123',
  };

  const httpResponse: HttpResponse<UserDTO> = new HttpResponse({ body: mockUser });

  beforeEach(async () => {
    const userServiceSpy = jasmine.createSpyObj('UserControllerService', ['create']);

    await TestBed.configureTestingModule({
      imports: [
        ReactiveFormsModule,
        FormsModule,
        MatFormFieldModule,
        RouterTestingModule.withRoutes([]),
      ],
      declarations: [RegisterComponent],
      providers: [
        { provide: UserControllerService, useValue: userServiceSpy },
        { provide:LocalStorageService, useValue: localStorageService }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(RegisterComponent);
    component = fixture.componentInstance;
    userService = TestBed.inject(UserControllerService) as jasmine.SpyObj<UserControllerService>;
    router = TestBed.inject(Router);
  });

  it('should create the component', () => {
    const fixture = TestBed.createComponent(RegisterComponent);
    const app = fixture.componentInstance
    expect(app).toBeTruthy();
  });

  it('should return false since it does not meet the required fields', fakeAsync(() => {
    const fixture = TestBed.createComponent(RegisterComponent);
    const app = fixture.componentInstance
    fixture.detectChanges() //TODO: <---------------

    const email = app.registerForm.controls['email']
    email.setValue('leifer33@gmail.com')

    expect(app.registerForm.invalid).toBeTrue(); //TODO: ✔
  }));

  it('should return true with valid fields', fakeAsync(() => {
    const fixture = TestBed.createComponent(RegisterComponent);
    const app = fixture.componentInstance
    fixture.detectChanges()

    let email = app.registerForm.controls['email']
    let password = app.registerForm.controls['password']
    let repeatPassword = app.registerForm.controls['repeatPassword']
    let role = app.registerForm.controls['role']
    let dni = app.registerForm.controls['dni']
    let phone = app.registerForm.controls['phone']
    let address = app.registerForm.controls['address']
    let firstname = app.registerForm.controls['firstname']
    let lastname = app.registerForm.controls['lastname']

    email.setValue('leifer33@gmail.com')
    password.setValue('123456')
    repeatPassword.setValue('123456')
    role.setValue('ADMIN')
    dni.setValue('232323223')
    phone.setValue('232323223')
    address.setValue('CALLE FALSA')
    firstname.setValue('Charlie')
    lastname.setValue('Zolezzi')
    expect(app.registerForm.invalid).toBeFalse(); //TODO: ✔
  }));
});