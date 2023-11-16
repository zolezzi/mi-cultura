import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LocalStorageService } from 'ngx-webstorage';
import { UserControllerService } from 'src/app/api';

interface Roles {
  value: string;
  viewValue: string;
}

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
})
export class RegisterComponent implements OnInit {
  registerForm!: FormGroup;
  submitted: boolean = false;
  private mapRoles = new Map<string, string>();

  constructor( private router: Router, private localStorageService: LocalStorageService, private formBuilder: FormBuilder,
    private userservice: UserControllerService
  ) {}

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      email: [
        '',
        [Validators.required, Validators.email]
      ],
      password: [
        '',
        Validators.required
      ],
      repeatPassword: [
        '',
        Validators.required
      ],
      role: ['', Validators.required],
      dni: ['', Validators.required],
      phone: ['', Validators.required],
      address: ['', Validators.required],
      firstname: ['', Validators.required],
      lastname: ['', Validators.required]
    });
    this.mapRoles.set('TURISTA', 'TOURIST');
    this.mapRoles.set('VISITANTE', 'VISITOR');
  }

  onSubmit() {
    this.submitted = true;
    if (this.registerForm.invalid) {
      return;
    }
    const user: any = {
      email: this.registerForm.controls['email'].value,
      password: this.registerForm.controls['password'].value,
      repeatPassword: this.registerForm.controls['repeatPassword'].value,
      account: {
        role: this.mapRoles.get(this.registerForm.controls['role'].value),
        dni: this.registerForm.controls['dni'].value,
        phoneNumber: this.registerForm.controls['phone'].value,
        address: this.registerForm.controls['address'].value,
        firstname: this.registerForm.controls['firstname'].value,
        lastname: this.registerForm.controls['lastname'].value
      },
    };
    this.userservice.create(user).subscribe({
      next: (result) => {
        this.goToLogin();
      },
      error: (error) =>
        console.error('Error creando la cuenta. Reintente nuevamente.'),
    });
  }

  private goToLogin() {
    this.router.navigate(['/login']);
  }

  register_validation_messages = {
    firstname: [
      { type: 'required', message: 'Nombre es requerido.' },
      { type: 'pattern', message: 'Nombre es requerido.' },
    ],
    lastname: [
      { type: 'required', message: 'Apellido es requerido.' },
      { type: 'pattern', message: 'Apellido es requerido.' },
    ],
    email: [{ type: 'required', message: 'Email es requerido.' }],
    dni: [{ type: 'required', message: 'DNI es requerido.' }],
    phone: [{ type: 'required', message: 'Telefono es requerido.' }],
    address: [{ type: 'required', message: 'Dirección es requerido.' }],
    password: [
      { type: 'required', message: 'Contraseña es requerida.' },
      {
        type: 'pattern',
        message:
          'Contraseña deberia tener una mayúscula, una minúscula y un número.',
      },
    ],
    repeatPassword: [{ type: 'required', message: 'Confirmar  es requerido.' }],
    role:[{ type: 'required', message: 'Es requerido.' }]
  };
}