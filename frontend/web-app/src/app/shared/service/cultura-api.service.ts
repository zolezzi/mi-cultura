import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CulturaAPIService {
  
  private readonly BASE_URL: string = 'https://www.cultura.gob.ar/api/v2.0/';

  constructor(private http: HttpClient) {

  }

  public getMuseums(): Observable<any> {   
    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')
    const url = this.BASE_URL +'museos/?limit=30';
    var data = this.http.get(url, {'headers': headers });
    return data;
  }

  public getInstitutes(): Observable<any> {   
    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')
    const url = this.BASE_URL +'institutos/';
    var data = this.http.get(url, {'headers': headers });
    return data;
  }
  
  public getAgencies(): Observable<any> {   
    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')
    const url = this.BASE_URL +'organismos/?limit=20&offset=120';
    var data = this.http.get(url, {'headers': headers });
    return data;
  }

  public getAnnouncements(): Observable<any> {   
    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')
    const url = this.BASE_URL +'convocatorias/?limit=20&offset=120';
    var data = this.http.get(url, {'headers': headers });
    return data;
  }

  public getProcedures(): Observable<any> {   
    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')
    const url = this.BASE_URL +'tramites/';
    var data = this.http.get(url, {'headers': headers });
    return data;
  }
}