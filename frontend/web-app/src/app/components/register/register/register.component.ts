import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LocalStorageService } from 'ngx-webstorage';

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

  constructor( private router: Router, private localStorageService: LocalStorageService, private formBuilder: FormBuilder
  ) {}

  ngOnInit(): void {
    this.registerForm = this.formBuilder.group({
      email: [
        '',
        Validators.compose([
          Validators.required,
          Validators.pattern('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$'),
        ]),
      ],
      password: [
        '',
        Validators.compose([Validators.required, Validators.minLength(8)]),
      ],
      repeatPassword: [
        '',
        Validators.compose([Validators.required, Validators.minLength(8)]),
      ],
      role: ['', Validators.compose([Validators.required, ])],
      dni: ['', Validators.compose([Validators.required, Validators.pattern('/^-?(0|[1-9]\d*)?$/')])],
      phone: ['', Validators.compose([Validators.required, , Validators.pattern('/^-?(0|[1-9]\d*)?$/')])],
      address: ['', Validators.compose([Validators.required, ])],
      firstname: ['', Validators.compose([Validators.required, ])],
      lastname: ['', Validators.compose([Validators.required])],
      degreeId: [[]],
    });
    this.mapRoles.set('Estudiante', 'STUDENT');
    this.mapRoles.set('Profesor', 'PROFESSOR');
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
        phone: this.registerForm.controls['dni'].value,
        address: this.registerForm.controls['dni'].value,
        firstname: this.registerForm.controls['firstname'].value,
        lastname: this.registerForm.controls['lastname'].value,
        degreeId: this.registerForm.controls['degreeId'].value,
      },
    };
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