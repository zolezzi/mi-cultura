import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { PageEvent } from '@angular/material/paginator';
import { LocalStorageService } from 'ngx-webstorage';
import { CulturaAPIService } from 'src/app/shared/service/cultura-api.service';

@Component({
  selector: 'app-place-search',
  templateUrl: './place-search.component.html',
  styleUrls: ['./place-search.component.css']
})
export class PlaceSearchComponent implements OnInit{

  isAdmin: boolean = false;
  places: any[] = [];
  pageSize = 10;
  pageIndex = 0;
  totalItems = 0;
  role!: string;
  private readonly ACCESS_TOKEN: string = 'ACCESS_TOKEN';
  private readonly ROLE: string = 'ROLE';
  
  constructor(private router: Router, private localStorageService: LocalStorageService, private culturaAPIService :CulturaAPIService) {

  }

  ngOnInit(): void {
    this.role = this.localStorageService.retrieve(this.ROLE);
    this.isAdmin = 'ADMIN' == this.role;
    const token = this.localStorageService.retrieve(this.ACCESS_TOKEN);
    
    this.culturaAPIService.getMuseums().subscribe((data) => {debugger
      console.log("DATA:" + data.results);
    });
    this.loadPlaces();
  }

  loadPlaces() {
    this.places = [
      {type: 'MUSEUM', description: `<p>El Complejo Hist&oacute;rico Cultural Manzana de las Luces depende del Ministerio de Cultura de la Naci&oacute;n.</p>`},
      {type: 'MUSEUM', description: `<p>El Complejo Hist&oacute;rico Cultural Manzana de las Luces depende del Ministerio de Cultura de la Naci&oacute;n.</p>\r\n<p>Fue creado en 1971 por el Decreto n&ordm; 4657/71 y ampliadas sus funciones por los decretos: 1185/73; 1454/74 y 1479/81. El decreto 108/2013 cambia su nombre de Comisi&oacute;n Nacional a Complejo Hist&oacute;rico Cultural Manzana de las Luces.</p>\r\n<p>Son sus objetivos la restauraci&oacute;n y conservaci&oacute;n de los edificios hist&oacute;ricos; la investigaci&oacute;n con relaci&oacute;n a instituciones, acontecimientos y personajes que desfilaron por la Manzana de las Luces; y la refuncionalizaci&oacute;n de los edificios a trav&eacute;s de la actividad cultural.</p>`},
      {type: 'MUSEUM', description: `<p>El Complejo Hist&oacute;rico Cultural Manzana de las Luces depende del Ministerio de Cultura de la Naci&oacute;n.</p>\r\n<p>Fue creado en 1971 por el Decreto n&ordm; 4657/71 y ampliadas sus funciones por los decretos: 1185/73; 1454/74 y 1479/81. El decreto 108/2013 cambia su nombre de Comisi&oacute;n Nacional a Complejo Hist&oacute;rico Cultural Manzana de las Luces.</p>\r\n<p>Son sus objetivos la restauraci&oacute;n y conservaci&oacute;n de los edificios hist&oacute;ricos; la investigaci&oacute;n con relaci&oacute;n a instituciones, acontecimientos y personajes que desfilaron por la Manzana de las Luces; y la refuncionalizaci&oacute;n de los edificios a trav&eacute;s de la actividad cultural.</p>`},
      {type: 'MUSEUM', description: `<p>El Complejo Hist&oacute;rico Cultural Manzana de las Luces depende del Ministerio de Cultura de la Naci&oacute;n.</p>\r\n<p>Fue creado en 1971 por el Decreto n&ordm; 4657/71 y ampliadas sus funciones por los decretos: 1185/73; 1454/74 y 1479/81. El decreto 108/2013 cambia su nombre de Comisi&oacute;n Nacional a Complejo Hist&oacute;rico Cultural Manzana de las Luces.</p>\r\n<p>Son sus objetivos la restauraci&oacute;n y conservaci&oacute;n de los edificios hist&oacute;ricos; la investigaci&oacute;n con relaci&oacute;n a instituciones, acontecimientos y personajes que desfilaron por la Manzana de las Luces; y la refuncionalizaci&oacute;n de los edificios a trav&eacute;s de la actividad cultural.</p>`},
      {type: 'MUSEUM', description: `<p>El Complejo Hist&oacute;rico Cultural Manzana de las Luces depende del Ministerio de Cultura de la Naci&oacute;n.</p>\r\n<p>Fue creado en 1971 por el Decreto n&ordm; 4657/71 y ampliadas sus funciones por los decretos: 1185/73; 1454/74 y 1479/81. El decreto 108/2013 cambia su nombre de Comisi&oacute;n Nacional a Complejo Hist&oacute;rico Cultural Manzana de las Luces.</p>\r\n<p>Son sus objetivos la restauraci&oacute;n y conservaci&oacute;n de los edificios hist&oacute;ricos; la investigaci&oacute;n con relaci&oacute;n a instituciones, acontecimientos y personajes que desfilaron por la Manzana de las Luces; y la refuncionalizaci&oacute;n de los edificios a trav&eacute;s de la actividad cultural.</p>`},
      {type: 'MUSEUM', description: `<p>El Complejo Hist&oacute;rico Cultural Manzana de las Luces depende del Ministerio de Cultura de la Naci&oacute;n.</p>\r\n<p>Fue creado en 1971 por el Decreto n&ordm; 4657/71 y ampliadas sus funciones por los decretos: 1185/73; 1454/74 y 1479/81. El decreto 108/2013 cambia su nombre de Comisi&oacute;n Nacional a Complejo Hist&oacute;rico Cultural Manzana de las Luces.</p>\r\n<p>Son sus objetivos la restauraci&oacute;n y conservaci&oacute;n de los edificios hist&oacute;ricos; la investigaci&oacute;n con relaci&oacute;n a instituciones, acontecimientos y personajes que desfilaron por la Manzana de las Luces; y la refuncionalizaci&oacute;n de los edificios a trav&eacute;s de la actividad cultural.</p>`},
    ]
    this.totalItems = this.places.length;
  }

  delete(id: number) {}

  update(id: number) {}

  onPageChange(event: PageEvent) {
    this.pageIndex = event.pageIndex;
    this.pageSize = event.pageSize;
    //this.loadPlaces();
  }
}
