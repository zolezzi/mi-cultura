import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';
import { ProxyControllerService } from 'src/app/api/service/proxyController.service';

@Injectable({
  providedIn: 'root'
})
export class CulturaAPIService {

  constructor(private http: HttpClient, private proxyService: ProxyControllerService) {}

  public getMuseums(): Observable<any> {
    return this.proxyService.getMuseos();
  }

  public getInstitutes(): Observable<any> {
    return this.proxyService.getInstitutos();
  }
  
  public getAgencies(): Observable<any> {   
    return this.proxyService.getOrganismos();
  }

  public getAnnouncements(): Observable<any> {   
    return this.proxyService.getConvocatorias();
  }

  public getProcedures(): Observable<any> {   
    return this.proxyService.getTramites();
  }
}