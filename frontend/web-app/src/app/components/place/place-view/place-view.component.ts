import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LocalStorageService } from 'ngx-webstorage';
import { PlaceDTO } from 'src/app/api';
import { PlaceControllerService } from 'src/app/api/service/placeController.service';
import { PlaceCommunicationService } from 'src/app/shared/service/place-communication.service';

@Component({
  selector: 'app-place-view',
  templateUrl: './place-view.component.html',
  styleUrls: ['./place-view.component.css']
})
export class PlaceViewComponent implements OnInit{
  
  place: PlaceDTO = {};
  authority:any = {};
  VALUE_NULL: string = "Sin información";
  private readonly PLACE_VIEW_ADMIN_URL: string = "place-view-admin";
  private readonly PLACE_VIEW_ADMIN: string = 'PLACE_VIEW_ADMIN';

  constructor(private router: Router, private routerActived: ActivatedRoute, private localStorageService: LocalStorageService, 
    private placeService: PlaceControllerService, private placeCommunication:PlaceCommunicationService) {
      
  }

  ngOnInit(): void {
    // cuando hace click deberia buscar ese dato que guardé antes.
    this.routerActived.url.subscribe(params => {
      const url = params[0].path
      if(this.PLACE_VIEW_ADMIN_URL == url){
        this.place = this.localStorageService.retrieve(this.PLACE_VIEW_ADMIN);
        this.authority = this.place.authority;
      }
    });
  }

}
