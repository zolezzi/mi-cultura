import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from 'ngx-webstorage';

@Component({
  selector: 'app-navbar',
  templateUrl: './navbar.component.html',
  styleUrls: ['./navbar.component.css'],
})
export class NavbarComponent implements OnInit {
  isLoggedIn = false;
  fullName: string = '';
  role!: string;
  isAdmin: boolean = false;
  private readonly ACCESS_TOKEN: string = 'ACCESS_TOKEN';
  private readonly FULL_NAME:string = "FULL_NAME";
  private readonly ROLE: string = 'ROLE';

  constructor( private router: Router, private localStorageService: LocalStorageService) {}
 
  ngOnInit(): void {
    this.role = this.localStorageService.retrieve(this.ROLE);
    this.isAdmin = 'ADMIN' == this.role;
    const token = this.localStorageService.retrieve(this.ACCESS_TOKEN);
    this.isLoggedIn = !!token;
    this.fullName = this.localStorageService.retrieve(this.FULL_NAME);
  }

  login() {
    this.isLoggedIn = true;
  }

  logout() {
    this.localStorageService.clear(this.ACCESS_TOKEN);
    this.isLoggedIn = false;
    this.router.navigate(['/home']);
  }
}