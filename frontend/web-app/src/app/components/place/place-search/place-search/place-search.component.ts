import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PageEvent } from '@angular/material/paginator';
import { LocalStorageService } from 'ngx-webstorage';
import { CulturaAPIService } from 'src/app/shared/service/cultura-api.service';
import { PlaceControllerService } from 'src/app/api/service/placeController.service';
import { PlaceDTO } from 'src/app/api/model/placeDTO';
import { MatDialog } from '@angular/material/dialog';
import { MatSnackBar } from '@angular/material/snack-bar';
import { ConfirmDialogComponent } from 'src/app/components/confirm-dialog/confirm-dialog.component';


@Component({
  selector: 'app-place-search',
  templateUrl: './place-search.component.html',
  styleUrls: ['./place-search.component.css']
})
export class PlaceSearchComponent implements OnInit{

  isAdmin: boolean = false;
  isLoggedIn = false;
  userId!: number;
  places: PlaceDTO[] = [];
  pageSize = 10;
  pageIndex = 0;
  totalItems = 0;
  role!: string;
  private readonly ACCESS_TOKEN: string = 'ACCESS_TOKEN';
  private readonly ROLE: string = 'ROLE';
  private readonly USER_ID: string = 'USER_ID';
  private readonly PLACE_VIEW_ADMIN: string = 'PLACE_VIEW_ADMIN';
  
  constructor(private router: Router, private localStorageService: LocalStorageService, 
    private culturaAPIService :CulturaAPIService, private placeService: PlaceControllerService,
    private snackBar: MatSnackBar, private dialog: MatDialog) {
      
  }

  ngOnInit(): void {
    this.role = this.localStorageService.retrieve(this.ROLE);
    this.isAdmin = 'ADMIN' == this.role;
    const token = this.localStorageService.retrieve(this.ACCESS_TOKEN);
    this.isLoggedIn = !!token;
    this.userId = this.localStorageService.retrieve(this.USER_ID);
    this.loadAllPlaces();
  }

  loadAllPlaces() {
    this.culturaAPIService.getMuseums().subscribe((data) => {
      this.places = this.places.concat(this.mapDataMuseums(data.results));
      this.culturaAPIService.getInstitutes().subscribe((data) => {
        this.places = this.places.concat(this.mapDataInstitutes(data.results));
        this.culturaAPIService.getAgencies().subscribe((data) => {
          this.places = this.places.concat(this.mapDataAgencies(data.results));
          this.totalItems = this.places.length;
          //this.placeService.findAll(this.ACCESS_TOKEN).subscribe((result) => {
          //  this.places.concat(result);
          //});
        });
      });
      //this.places.concat();
    });
  }

  mapDataAgencies(institutes:any){
    return institutes.map((item:any) => {  
      var placeI = {} as PlaceDTO;
      placeI.id = item.id;
      placeI.name = item.nombre;
      placeI.placeType = 'AGENCIES';
      placeI.address = item.direccion;
      placeI.phoneNumber = item.telefono;
      placeI.isFavorite = false;
      return placeI;
    });
  }

  mapDataInstitutes(institutes:any){
    return institutes.map((item:any) => {  
      var placeI = {} as PlaceDTO;
      placeI.id = item.id;
      placeI.name = item.nombre;
      placeI.placeType = 'INSTITUTE';
      placeI.address = item.direccion;
      placeI.phoneNumber = item.telefono;
      placeI.description = item.descripcion;
      placeI.email = item.email;
      placeI.placeTypeDescription = "INSTITUCIÓN";
      placeI.isFavorite = false;
      return placeI;
    });
  }

  mapDataMuseums(museums:any){
    return museums.map( (item:any) => {  
      var placeI = {} as PlaceDTO;
      placeI.id = item.id;
      placeI.name = item.nombre;
      placeI.placeType = 'MUSEUM';
      placeI.address = item.direccion;
      placeI.phoneNumber = item.telefono;
      placeI.description = item.descripcion;
      placeI.email = item.email;
      placeI.placeTypeDescription = "INSTITUCIÓN";
      placeI.province = item.provincia;
      placeI.dependsOn = item.depende_de;
      placeI.link = item.link;
      placeI.url = item.url;
      placeI.isFavorite = false;
      return placeI;
    });
  }

  delete(place :any) {
    var userId = 1;
    var id = 1;
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '350px',
      data: { message: 'Está seguro que desea sacar de sus favoritos este lugar?' },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result === 'aceptar') {
        this.placeService
          .removeFavorite(this.localStorageService.retrieve(this.ACCESS_TOKEN), id, userId)
          .subscribe((data:any) => {
            this.snackBar.open('Borrado con éxito', '', {
              duration: 10000,
              horizontalPosition: 'center',
              verticalPosition: 'bottom',
            });
            //this.search(this.filter);
          });
      }
    });
  }

  update(id: number) {}

  view(place: any) {
    if(!this.isLoggedIn){
      this.router.navigate(['/login']);
    }
    if(!this.isAdmin){
      this.localStorageService.store(this.PLACE_VIEW_ADMIN, place);
      this.router.navigate(['/place-view-admin/']);
    } else{
        this.placeService.save(this.ACCESS_TOKEN, place.id, place, this.userId).subscribe((result) => {
          this.places.concat(result);
        });
    }

  }

  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    //this.loadPlaces();
  }
}
