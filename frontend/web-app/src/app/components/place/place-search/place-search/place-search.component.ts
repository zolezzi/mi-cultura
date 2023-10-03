import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { LocalStorageService } from 'ngx-webstorage';

@Component({
  selector: 'app-place-search',
  templateUrl: './place-search.component.html',
  styleUrls: ['./place-search.component.css']
})
export class PlaceSearchComponent implements OnInit{

  isAdmin: boolean = false;
  places: any[] = [];
  private readonly ACCESS_TOKEN: string = 'ACCESS_TOKEN';
  private readonly ROLE: string = 'ROLE';

  constructor( private router: Router, private localStorageService: LocalStorageService) {

  }

  ngOnInit(): void {

  }

  delete(id: number) {}

  update(id: number) {}
}
