import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CulturaAPIService {
  
  constructor(private http: HttpClient) {

  }

  public getMuseums(): Observable<any> {debugger
    
    const headers = new HttpHeaders()
      .set('Content-Type', 'application/json')
    const url = 'https://www.cultura.gob.ar/api/v2.0/museos/?limit=30';
    var data = this.http.get(url, {'headers': headers });
    return data;
}
}