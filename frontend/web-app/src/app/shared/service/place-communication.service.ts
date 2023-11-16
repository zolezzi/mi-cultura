import { Observable, Subject } from 'rxjs';
import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class PlaceCommunicationService {

    private subject = new Subject<void>();

    sendPlace(place: any) {
        this.subject.next(place);
    }
 
    clearPlace() {
        this.subject.next();
    }
 
    getPlace(): Observable<any> {
        return this.subject.asObservable();
    }
}