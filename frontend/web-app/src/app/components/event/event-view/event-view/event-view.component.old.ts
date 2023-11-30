import { ComponentFixture, TestBed, tick, fakeAsync } from '@angular/core/testing';
import { EventViewComponent } from './event-view.component';
import { RouterTestingModule } from '@angular/router/testing';
import { ActivatedRoute, convertToParamMap } from '@angular/router';
import { of } from 'rxjs';
import { BarRatingModule } from "ngx-bar-rating";
import { LocalStorageService } from 'ngx-webstorage';
import { EventControllerService } from 'src/app/api/service/eventController.service';
import { ReviewControllerService } from 'src/app/api/service/reviewController.service';
import { EventDTO } from 'src/app/api/model/eventDTO';
import { ReviewVO } from 'src/app/api/model/reviewVO';
import { HttpEvent, HttpEventType, HttpResponse } from '@angular/common/http';
import { ReviewDTO } from 'src/app/api/model/reviewDTO';
import { MatFormFieldModule } from '@angular/material/form-field';
import { SanitizeHtmlPipe } from 'src/app/shared/pipe/dom-sanitizer.component';
import { FormsModule } from '@angular/forms';

describe('EventViewComponent', () => {
  let component: EventViewComponent;
  let fixture: ComponentFixture<EventViewComponent>;
  let localStorageService: any;
  let eventService: jasmine.SpyObj<EventControllerService>;
  let reviewService: jasmine.SpyObj<ReviewControllerService>;
  const fakeEvent: EventDTO = {
    body:'body',
    dependsOn:'dependsOn',
    email:'email',
    eventId:1,
    eventType:'',
    eventTypeDescription:'',
    fromDate:'',
    id: 1,
    image:'',
    isFavorite: false,
    link: '',
    state:  '',
    subTitle: '',
    title:  '',
    toDate:  '',
    url: ''
  };

  const fakeReviewDto: ReviewDTO = {
    comments: '',
    score: 1
  };

  const fakeReview: ReviewVO = {
    comments: '',
    score: 1
  };

  const httpResponseScore: HttpResponse<Number> = new HttpResponse({ body: 5 });
  const httpResponseEvent: HttpResponse<EventDTO> = new HttpResponse({ body: fakeEvent });
  const httpResponseReview: HttpResponse<ReviewDTO> = new HttpResponse({ body: fakeReviewDto });

  beforeEach(async () => {
    const localStorageServiceSpy = jasmine.createSpyObj('LocalStorageService', ['retrieve', 'store']);
    const eventServiceSpy = jasmine.createSpyObj('EventControllerService', ['getTotalReviewScore', 'findById', 'favorite', 'removeFavorite', 'update']);
    const reviewServiceSpy = jasmine.createSpyObj('ReviewControllerService', ['getReviewEvent']);

    await TestBed.configureTestingModule({
      imports: [RouterTestingModule, BarRatingModule, MatFormFieldModule, FormsModule ],
      declarations: [EventViewComponent, SanitizeHtmlPipe],
      providers: [
        { provide: LocalStorageService, useValue: {
          retrieve: jasmine.createSpy('retrieve').and.callFake((key: string) => {
            if (key === 'ROLE') {
              return 'ADMIN';
            } else if (key === 'ACCESS_TOKEN') {
              return 'adasdasdasdasdasds';
            } else if (key === 'FULL_NAME') {
              return 'Han Solo';
            }else if (key === 'USER_ID') {
              return 1;
            }
            return null;
          })
        } },
        { provide: EventControllerService, useValue: eventServiceSpy },
        { provide: ReviewControllerService, useValue: reviewServiceSpy },
        {
          provide: ActivatedRoute, useValue: {
            url: of([{ path: 'admin' }]),
            snapshot: { paramMap: convertToParamMap({ id: '1' }) }
          }
        }
      ]
    }).compileComponents();

    fixture = TestBed.createComponent(EventViewComponent);
    component = fixture.componentInstance;
    localStorageService = TestBed.inject(LocalStorageService);
    eventService = TestBed.inject(EventControllerService) as jasmine.SpyObj<EventControllerService>;
    reviewService = TestBed.inject(ReviewControllerService) as jasmine.SpyObj<ReviewControllerService>;

    localStorageService.retrieve.withArgs('ROLE').and.returnValue('ADMIN');
    localStorageService.retrieve.withArgs('ACCESS_TOKEN').and.returnValue('fakeToken');
    localStorageService.retrieve.withArgs('ACCOUNT_ID').and.returnValue('fakeAccountId');
    //eventService.getTotalReviewScore.and.returnValue(of(httpResponseScore));
    eventService.findById.and.returnValue(of(httpResponseEvent));
    reviewService.getReviewEvent.and.returnValue(of(httpResponseReview));
  });

  it('should create component and load event data for admin', fakeAsync(() => {
    fixture.detectChanges();
    tick();

    expect(component).toBeTruthy();
    expect(component.isAdmin).toBeTrue();
    expect(component.isLoggedIn).toBeTrue();
    //expect(component.totalRate).toBe(5);
    expect(component.isLoadEvent).toBeTrue();
  }));

});