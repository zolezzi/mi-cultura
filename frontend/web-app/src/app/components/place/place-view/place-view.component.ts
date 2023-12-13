import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LocalStorageService } from 'ngx-webstorage';
import { PlaceDTO, ReviewVO } from 'src/app/api';
import { PlaceControllerService } from 'src/app/api/service/placeController.service';
import { ReviewControllerService } from 'src/app/api/service/reviewController.service';
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
  isAdmin: boolean = false;
  isLoggedIn = false;
  userId!: number;
  role!: string;
  id!: number;
  currentRate : number = 0;
  totalRate = 0;
  comments:string = '';
  private readonly PLACE_VIEW_ADMIN_URL: string = "place-view-admin";
  private readonly PLACE_VIEW_ADMIN: string = 'PLACE_VIEW_ADMIN';
  private readonly PLACE_VIEW_REMOVE: string = 'PLACE_VIEW_REMOVE';
  private readonly ROLE: string = 'ROLE';
  private readonly USER_ID: string = 'USER_ID';
  private readonly ACCESS_TOKEN: string = 'ACCESS_TOKEN';
  private readonly ACCOUNT_ID: string = 'ACCOUNT_ID';

  constructor(private router: Router, private routerActived: ActivatedRoute, private localStorageService: LocalStorageService, 
    private placeService: PlaceControllerService, private placeCommunication:PlaceCommunicationService, private reviewService : ReviewControllerService) {
      
  }

  ngOnInit(): void {
    this.role = this.localStorageService.retrieve(this.ROLE);
    this.isAdmin = 'ADMIN' == this.role;
    const token = this.localStorageService.retrieve(this.ACCESS_TOKEN);
    this.isLoggedIn = !!token;
    this.userId = this.localStorageService.retrieve(this.USER_ID);
    this.routerActived.url.subscribe(params => {
      const url = params[0].path
      const token = this.localStorageService.retrieve(this.ACCESS_TOKEN);
      if(this.PLACE_VIEW_ADMIN_URL == url){
        this.place = this.localStorageService.retrieve(this.PLACE_VIEW_ADMIN);
        this.authority = this.place.authority;
        this.placeService.getTotalReviewScore(token, this.id).subscribe((result) => {
          this.totalRate = result; 
        });
      }else{
        this.id = Number(this.routerActived.snapshot.paramMap.get('id'));
        this.placeService.getTotalReviewScore(this.localStorageService.retrieve(this.ACCESS_TOKEN), this.id).subscribe((result) => {
          this.totalRate = result; 
        });
        this.placeService.findById(token, this.id).subscribe((result) => {
          this.place = result; 
          this.reviewService.getReview(this.localStorageService.retrieve(this.ACCOUNT_ID), this.localStorageService.retrieve(this.ACCESS_TOKEN), Number(this.place.placeId)).subscribe((result) => {
            this.currentRate = Number(result.score);
            this.comments = String(result.comments);
          });
        });
      }
    });
  }

  isFavourite(){ 
    this.place.isFavorite = true;
    var value_id = this.place.placeId;
    this.placeService.favorite(this.localStorageService.retrieve(this.ACCOUNT_ID), this.localStorageService.retrieve(this.ACCESS_TOKEN), this.place, Number(value_id)).subscribe((result) => {
      this.place = result; 
    });
  }

  removeFavourite(){
    this.place.isFavorite = false;
    this.localStorageService.store(this.PLACE_VIEW_REMOVE, this.place);
    this.placeService.removeFavorite(this.localStorageService.retrieve(this.ACCOUNT_ID), this.localStorageService.retrieve(this.ACCESS_TOKEN), Number(this.place.placeId)).subscribe((result) => {
      this.place = this.localStorageService.retrieve(this.PLACE_VIEW_REMOVE); 
    });
  }

  sendReview(){
    var review :ReviewVO = {};
    review.score = this.currentRate;
    review.comments = this.comments;
    this.placeService.update(this.localStorageService.retrieve(this.ACCESS_TOKEN), Number(this.place.placeId), review, this.userId).subscribe((result) => {
      this.place = result; 
      this.ngOnInit();
    });
  }

}
