import { Component, OnInit } from '@angular/core';
import { PageEvent } from '@angular/material/paginator';
import { Router } from '@angular/router';
import { LocalStorageService } from 'ngx-webstorage';
import { AccountReviewDetailsDTO } from 'src/app/api';
import { ReviewControllerService } from 'src/app/api/service/reviewController.service';

@Component({
  selector: 'app-review-search',
  templateUrl: './review-search.component.html',
  styleUrls: ['./review-search.component.css']
})
export class ReviewSearchComponent implements OnInit{
  
  reviews: AccountReviewDetailsDTO[] = [];
  pageSize = 10;
  pageIndex = 0;
  totalItems = 0;
  private readonly ACCESS_TOKEN: string = 'ACCESS_TOKEN';

  constructor(private router: Router, private localStorageService: LocalStorageService, 
    private reviewService: ReviewControllerService) {
      
  }

  ngOnInit(): void {
    this.reviewService.findAll(this.localStorageService.retrieve(this.ACCESS_TOKEN)).subscribe((reviewsDB) => {
      this.reviews = reviewsDB;
      this.totalItems = this.reviews.length;
    });
  }

  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
  }
}
