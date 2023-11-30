import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PageEvent } from '@angular/material/paginator';
import { LocalStorageService } from 'ngx-webstorage';
import { CulturaAPIService } from 'src/app/shared/service/cultura-api.service';
import { EventControllerService } from 'src/app/api/service/eventController.service';
import { EventDTO } from 'src/app/api/model/eventDTO';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';

@Component({
  selector: 'app-event-search',
  templateUrl: './event-search.component.html',
  styleUrls: ['./event-search.component.css']
})
export class EventSearchComponent implements OnInit{

  isAdmin: boolean = false;
  isLoggedIn = false;
  userId!: number;
  events: EventDTO[] = [];
  pageSize = 10;
  pageIndex = 0;
  totalItems = 0;
  role!: string;
  private readonly ACCESS_TOKEN: string = 'ACCESS_TOKEN';
  private readonly ROLE: string = 'ROLE';
  private readonly USER_ID: string = 'USER_ID';
  private readonly ACCOUNT_ID: string = 'ACCOUNT_ID';
  private readonly EVENT_VIEW_ADMIN: string = 'EVENT_VIEW_ADMIN';

  constructor(private router: Router, private localStorageService: LocalStorageService, 
    private culturaAPIService :CulturaAPIService, private eventService: EventControllerService,
    private snackBar: MatSnackBar, private dialog: MatDialog) {
      
  }

  ngOnInit(): void {
    this.role = this.localStorageService.retrieve(this.ROLE);
    this.isAdmin = 'ADMIN' == this.role;
    const token = this.localStorageService.retrieve(this.ACCESS_TOKEN);
    this.isLoggedIn = !!token;
    this.userId = this.localStorageService.retrieve(this.USER_ID);
    this.loadAllEvents();
  }

  loadAllEvents(){
    this.events = [];
    this.culturaAPIService.getAnnouncements().subscribe((data) => {
      this.events = this.events.concat(this.mapDataAnnouncements(data.results));
      this.culturaAPIService.getProcedures().subscribe((data) => {
        this.events = this.events.concat(this.mapDataProcedures(data.results));
        this.totalItems = this.events.length;
        this.eventService.findAllByUserId(this.localStorageService.retrieve(this.ACCESS_TOKEN), this.localStorageService.retrieve(this.USER_ID)).subscribe((eventsDB) => {
          this.events = this.mergeWithEventsInTheDataBase(eventsDB, this.events);
          this.totalItems = this.events.length;
        });

      });
    });
  }

  mergeWithEventsInTheDataBase(eventsDB:EventDTO[], events:EventDTO[]){
    var listResult = eventsDB;
    var listIds = eventsDB.map(event => event.eventId);
    for(const eventI of events){
      if(!listIds.includes(eventI.id)){
        listResult.push(eventI);
      }
    }
    return listResult;
  }

  mapDataAnnouncements(announcements:any) {
    return announcements.map((item:any) => {  
      var eventI = {} as EventDTO;
      eventI.id = item.id;
      eventI.eventId = item.id;
      eventI.url = item.url;
      eventI.link = item.link;
      eventI.image = item.imagen;
      eventI.eventType = 'ANNOUNCEMENT';
      eventI.state = item.estado;
      eventI.fromDate = item.fecha_inicio;
      eventI.toDate = item.fecha_fin;
      eventI.title = item.titulo;
      eventI.subTitle = item.bajada;
      eventI.body = item.cuerpo;
      eventI.isFavorite = false;
      return eventI;
    });
  }

  mapDataProcedures(procedures:any) {
    return procedures.map((item:any) => {  
      var eventI = {} as EventDTO;
      eventI.id = item.id;
      eventI.eventId = this.getProcedureId(item.url);
      eventI.url = item.url;
      eventI.link = item.link;
      eventI.image = item.imagen;
      eventI.state = item.estado;
      eventI.fromDate = item.fecha_inicio;
      eventI.toDate = item.fecha_fin;
      eventI.title = item.titulo;
      eventI.eventType = 'PROCEDURE';
      eventI.state = item.estado;
      eventI.fromDate = item.fecha_inicio;
      eventI.toDate = item.fecha_fin;
      eventI.title = item.titulo;
      eventI.subTitle = item.bajada;
      eventI.body = item.cuerpo;
      eventI.isFavorite = false;
      return eventI;
    });
  }

  getProcedureId(url:String) : number{
    var listData = url.split("/");
    return Number(listData[listData.length - 1]);
  }

  view(event: any) {
    if(!this.isLoggedIn){
      this.router.navigate(['/login']);
    }
    if(this.isAdmin){
      this.localStorageService.store(this.EVENT_VIEW_ADMIN, event);
      this.router.navigate(['/event-view-admin/']);
    } else{
        this.eventService.save(this.localStorageService.retrieve(this.ACCESS_TOKEN), event.eventId, event, this.userId).subscribe((result) => {
          this.router.navigate(['event-view/' + event.eventId])
        });
    }
  }

  favorite(event:any){  
    event.isFavorite = true;
    var value_id = event.eventId;
    this.eventService.favorite(this.localStorageService.retrieve(this.ACCOUNT_ID), this.localStorageService.retrieve(this.ACCESS_TOKEN), event, Number(value_id)).subscribe((result) => {
      this.loadAllEvents();
    });
  }

  delete(event :any) {
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '350px',
      data: { message: 'Está seguro que desea sacar de sus favoritos este evento?' },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result === 'aceptar') {
        this.eventService
          .removeFavorite(this.localStorageService.retrieve(this.ACCOUNT_ID), this.localStorageService.retrieve(this.ACCESS_TOKEN), event.id )
          .subscribe((data:any) => {
            this.snackBar.open('Borrado con éxito', '', {
              duration: 10000,
              horizontalPosition: 'center',
              verticalPosition: 'bottom',
            });
            this.loadAllEvents();
          });
      }
    });
  }

  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
  }
}
