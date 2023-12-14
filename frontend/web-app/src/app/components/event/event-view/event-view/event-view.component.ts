import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { LocalStorageService } from 'ngx-webstorage';
import { EventDTO } from 'src/app/api/model/eventDTO';
import { ReviewVO } from 'src/app/api/model/reviewVO';
import { EventControllerService } from 'src/app/api/service/eventController.service';
import { ReviewControllerService } from 'src/app/api/service/reviewController.service';
import party from "party-js";

@Component({
  selector: 'app-event-view',
  templateUrl: './event-view.component.html',
  styleUrls: ['./event-view.component.css']
})
export class EventViewComponent implements OnInit{

  event: EventDTO = {};
  isLoadEvent: boolean = false;
  VALUE_NULL: string = "Sin información";
  isAdmin: boolean = false;
  isLoggedIn = false;
  userId!: number;
  role!: string;
  id!: number;
  currentRate : number = 0;
  totalRate = 0;
  comments:string = "";
  private readonly EVENT_VIEW_ADMIN_URL: string = "event-view-admin";
  private readonly EVENT_VIEW_ADMIN: string = 'EVENT_VIEW_ADMIN';
  private readonly EVENT_VIEW_REMOVE: string = 'EVENT_VIEW_REMOVE';
  private readonly ROLE: string = 'ROLE';
  private readonly USER_ID: string = 'USER_ID';
  private readonly ACCESS_TOKEN: string = 'ACCESS_TOKEN';
  private readonly ACCOUNT_ID: string = 'ACCOUNT_ID';

  constructor(private router: Router, private routerActived: ActivatedRoute, private localStorageService: LocalStorageService, 
    private eventService: EventControllerService, private reviewService : ReviewControllerService) {
      
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
      if(this.EVENT_VIEW_ADMIN_URL == url){
        this.event = this.localStorageService.retrieve(this.EVENT_VIEW_ADMIN);
        this.isLoadEvent = true
        this.eventService.getTotalReviewScore(token, this.id).subscribe((result) => {
          this.totalRate = result; 
        });
      }else{
        this.id = Number(this.routerActived.snapshot.paramMap.get('id'));
        this.eventService.getTotalReviewScore(this.localStorageService.retrieve(this.ACCESS_TOKEN), this.id).subscribe((result) => {
          this.totalRate = result; 
        });
        this.eventService.findById(token, this.id).subscribe((result) => {
          this.event = result;
          this.isLoadEvent = true
          this.reviewService.getReviewEvent(this.localStorageService.retrieve(this.ACCOUNT_ID), this.localStorageService.retrieve(this.ACCESS_TOKEN), Number(this.event.eventId)).subscribe((result) => {
            this.currentRate = Number(result.score);
            this.comments = String(result.comments);
          });
        });
      }
    });
  }

  isFavourite(){ 
    this.event.isFavorite = true;
    var value_id = this.event.eventId;
    this.eventService.favorite(this.localStorageService.retrieve(this.ACCOUNT_ID), this.localStorageService.retrieve(this.ACCESS_TOKEN), this.event, Number(value_id)).subscribe((result) => {
      this.event = result; 
    });
  }

  removeFavourite(){
    this.event.isFavorite = false;
    this.localStorageService.store(this.EVENT_VIEW_REMOVE, this.event);
    this.eventService.removeFavorite(this.localStorageService.retrieve(this.ACCOUNT_ID), this.localStorageService.retrieve(this.ACCESS_TOKEN), Number(this.event.eventId)).subscribe((result) => {
      this.event = this.localStorageService.retrieve(this.EVENT_VIEW_REMOVE); 
    });
  }

  sendReview(event:any){
    var review :ReviewVO = {};
    review.score = this.currentRate;
    review.comments = this.comments;
    this.eventService.update(this.localStorageService.retrieve(this.ACCESS_TOKEN), Number(this.event.eventId), review, this.userId).subscribe((result) => {
      this.event = result; 
      this.ngOnInit();
      party.confetti(event);
    });
  }

}
