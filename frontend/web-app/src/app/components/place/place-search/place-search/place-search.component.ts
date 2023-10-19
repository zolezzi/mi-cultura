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
  places: PlaceDTO[] = [];
  pageSize = 10;
  pageIndex = 0;
  totalItems = 0;
  role!: string;
  private readonly ACCESS_TOKEN: string = 'ACCESS_TOKEN';
  private readonly ROLE: string = 'ROLE';
  
  constructor(private router: Router, private localStorageService: LocalStorageService, 
    private culturaAPIService :CulturaAPIService, private placeService: PlaceControllerService,
    private snackBar: MatSnackBar, private dialog: MatDialog) {
      
  }

  ngOnInit(): void {
    this.role = this.localStorageService.retrieve(this.ROLE);
    this.isAdmin = 'ADMIN' == this.role;
    const token = this.localStorageService.retrieve(this.ACCESS_TOKEN);
    this.isLoggedIn = !!token;
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
      placeI.isFavorite = false;
      return placeI;
    });
  }

  delete(place :any) {
    var userId = 1;
    var id = 1;
    const dialogRef = this.dialog.open(ConfirmDialogComponent, {
      width: '350px',
      data: { message: 'Esta seguro que desea eliminar el lugar?' },
    });
    dialogRef.afterClosed().subscribe((result) => {
      if (result === 'aceptar') {
        this.placeService
          .deleteById(this.localStorageService.retrieve(this.ACCESS_TOKEN), id, userId)
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

  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    //this.loadPlaces();
  }
}
