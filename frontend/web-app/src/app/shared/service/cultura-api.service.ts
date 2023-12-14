import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProxyControllerService } from 'src/app/api/service/proxyController.service';
import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class CulturaAPIService {
  private readonly BASE_URL: string = 'https://www.cultura.gob.ar/api/v2.0/';
  constructor(private http: HttpClient, private proxyService: ProxyControllerService) {}

  public getMuseums(): Observable<any> {
    if(environment.isE2E){
      const headers = new HttpHeaders().set('Content-Type', 'application/json')
      const url = this.BASE_URL +'institutos/';
      return this.http.get(url, {'headers': headers });
    }
    return this.proxyService.getMuseos();
  }

  public getInstitutes(): Observable<any> {
    if(environment.isE2E){
      const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')
    const url = this.BASE_URL +'museos/?limit=30';
      return this.http.get(url, {'headers': headers });
    }
    return this.proxyService.getInstitutos();
  }
  
  public getAgencies(): Observable<any> {
    if(environment.isE2E){
      const headers = new HttpHeaders().set('Content-Type', 'application/json')
      const url = this.BASE_URL +'organismos/?limit=20&offset=120';
      return this.http.get(url, {'headers': headers });
    }   
    return this.proxyService.getOrganismos();
  }

  public getAnnouncements(): Observable<any> { 
    if(environment.isE2E){
      const headers = new HttpHeaders().set('Content-Type', 'application/json')
      const url = this.BASE_URL +'convocatorias/?limit=20&offset=120';
      return this.http.get(url, {'headers': headers });
    }  
    return this.proxyService.getConvocatorias();
  }

  public getProcedures(): Observable<any> {
    if(environment.isE2E){
      const headers = new HttpHeaders().set('Content-Type', 'application/json')
      const url = this.BASE_URL +'tramites/';
      return this.http.get(url, {'headers': headers });
    }   
    return this.proxyService.getTramites();
  }
}