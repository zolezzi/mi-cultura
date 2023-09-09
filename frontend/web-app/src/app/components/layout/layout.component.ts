import { Component, OnInit } from '@angular/core';
import { LocalStorageService } from 'ngx-webstorage';

@Component({
  selector: 'app-layout',
  templateUrl: './layout.component.html',
  styleUrls: ['./layout.component.css']
})
export class LayoutComponent implements OnInit{
  
  constructor(private localStorageService: LocalStorageService) {

  }

  ngOnInit(): void {
    var isValid = this.localStorageService.retrieve('RELOAD');
    if (isValid == 'reload') {
      this.localStorageService.store('RELOAD', 'login');
      window.location.reload();
    }
  }

}
