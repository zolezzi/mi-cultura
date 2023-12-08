import { ComponentFixture, TestBed } from '@angular/core/testing';
import { LayoutComponent } from './layout.component';
import { LocalStorageService } from 'ngx-webstorage';
import { MatCardModule } from '@angular/material/card';

describe('LayoutComponent', () => {
  let component: LayoutComponent;
  let fixture: ComponentFixture<LayoutComponent>;
  let localStorageService: jasmine.SpyObj<LocalStorageService>;

  beforeEach(async () => {
    const localStorageServiceSpy = jasmine.createSpyObj('LocalStorageService', ['retrieve', 'store']);

    await TestBed.configureTestingModule({
      declarations: [LayoutComponent],
      imports: [MatCardModule],
      providers: [
        { provide: LocalStorageService, useValue: {
          retrieve: jasmine.createSpy('retrieve').and.returnValue('login'),
          store: jasmine.createSpy('store'),
        } }
      ]
    }).compileComponents();
  });

  beforeEach(() => {
    fixture = TestBed.createComponent(LayoutComponent);
    component = fixture.componentInstance;
    localStorageService = TestBed.inject(LocalStorageService) as jasmine.SpyObj<LocalStorageService>;
    fixture.detectChanges();
  });

  it('should not reload the page if localStorage does not have "RELOAD" equal to "reload"', () => {
    localStorageService.retrieve.and.returnValue('login');
    component.ngOnInit();
    expect(localStorageService.retrieve).toHaveBeenCalledWith('RELOAD');
  });
});