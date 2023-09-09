import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { LocalStorageService } from 'ngx-webstorage';
import { UserLoginVO } from 'src/app/api';
import { UserControllerService } from 'src/app/api/service/userController.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  loginForm!: FormGroup;
  submitted: boolean = false;
  private EMPTY: string = ' ';
  private readonly FULL_NAME: string = 'FULL_NAME';
  private readonly ROLE: string = 'ROLE';
  private readonly ACCESS_TOKEN: string = 'ACCESS_TOKEN';

  constructor( private userservice: UserControllerService, private router: Router,
    private localStorageService: LocalStorageService, private formBuilder: FormBuilder) {

  }

  ngOnInit(): void {
    this.loginForm = new FormGroup({
      email: new FormControl(
        '',
        Validators.compose([
          Validators.required,
          Validators.pattern('^[a-zA-Z0-9_.+-]+@[a-zA-Z0-9-]+.[a-zA-Z0-9-.]+$'),
        ])
      ),
      password: new FormControl(
        '',
        Validators.compose([
          Validators.minLength(8),
        ])
      ),
    });
  }

  onSubmit(value: any) {
    this.submitted = true;
    if (this.loginForm.invalid) {
      return;
    }
    const authRequest: UserLoginVO = {
      email: value.email,
      password: value.password,
    };
    this.userservice.login(authRequest).subscribe({
      next: (result) => {
        this.localStorageService.store(this.ACCESS_TOKEN, result.token);
        this.localStorageService.store(this.FULL_NAME, String(result.firstname + this.EMPTY + result.lastname));
        this.localStorageService.store(this.ROLE, result.role);
        this.goToWelcome();
      },
      error: (error) =>
        console.error('Error iniciando sesión. Reintente nuevamente.'),
    });
  }

  private goToWelcome() {
    this.localStorageService.store('RELOAD', 'reload');
    this.router.navigate(['/home']);
  }

  account_validation_messages = {
    email: [
      { type: 'required', message: 'Correo electrónico es requerido.' },
      { type: 'pattern', message: 'Ingrese un correo electrónico válido.' }
    ],

    password: [
      { type: 'required', message: 'Contraseña es requerida.' },
      { type: 'minlength', message: 'Contraseña deberia tener al menos 8 caracteres.'},
    ],
  };
}