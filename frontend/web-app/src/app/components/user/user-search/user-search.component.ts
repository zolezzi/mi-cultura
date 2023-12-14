import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from 'ngx-webstorage';
import { UserDTO } from 'src/app/api/model/userDTO';
import { AdminControllerService } from 'src/app/api/service/adminController.service';

@Component({
  selector: 'app-user-search',
  templateUrl: './user-search.component.html',
  styleUrls: ['./user-search.component.css']
})
export class UserSearchComponent implements OnInit{

  displayedColumns: string[] = ['email', 'firstname', 'lastname', 'roleDescripton'];
  users: UserDTO[] = [];
  private readonly ACCESS_TOKEN: string = 'ACCESS_TOKEN';

  constructor(private router: Router, private localStorageService: LocalStorageService, 
    private adminService: AdminControllerService) {
      
  }
  
  ngOnInit(): void {
    this.adminService.findAll(this.localStorageService.retrieve(this.ACCESS_TOKEN)).subscribe((data) => {
      this.users = data;
    });
  }

}
