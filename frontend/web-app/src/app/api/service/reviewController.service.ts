/**
 * Api Documentation
 * Api Documentation
 *
 * OpenAPI spec version: 1.0
 * 
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */
/* tslint:disable:no-unused-variable member-ordering */

import { Inject, Injectable, Optional }                      from '@angular/core';
import { HttpClient, HttpHeaders, HttpParams,
         HttpResponse, HttpEvent }                           from '@angular/common/http';

import { Observable }                                        from 'rxjs';

import { AccountReviewDetailsDTO } from '../model/accountReviewDetailsDTO';
import { ReviewDTO } from '../model/reviewDTO';

import { BASE_PATH, COLLECTION_FORMATS }                     from '../variables';
import { Configuration }                                     from '../configuration';


@Injectable()
export class ReviewControllerService {

    protected basePath = 'http://localhost:8080';
    public defaultHeaders = new HttpHeaders();
    public configuration = new Configuration();

    constructor(protected httpClient: HttpClient, @Optional()@Inject(BASE_PATH) basePath: string, @Optional() configuration: Configuration) {
        if (basePath) {
            this.basePath = basePath;
        }
        if (configuration) {
            this.configuration = configuration;
            this.basePath = basePath || configuration.basePath || this.basePath;
        }
    }

    /**
     * @param consumes string[] mime-types
     * @return true: consumes contains 'multipart/form-data', false: otherwise
     */
    private canConsumeForm(consumes: string[]): boolean {
        const form = 'multipart/form-data';
        for (let consume of consumes) {
            if (form === consume) {
                return true;
            }
        }
        return false;
    }


    /**
     * Service that returns all reviews
     * This service returns all reviews load
     * @param authorization 
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public findAll(authorization: string, observe?: 'body', reportProgress?: boolean): Observable<Array<AccountReviewDetailsDTO>>;
    public findAll(authorization: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<Array<AccountReviewDetailsDTO>>>;
    public findAll(authorization: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<Array<AccountReviewDetailsDTO>>>;
    public findAll(authorization: string, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {
        if (authorization === null || authorization === undefined) {
            throw new Error('Required parameter authorization was null or undefined when calling findAll3.');
        }

        let headers = this.defaultHeaders;
        if (authorization !== undefined && authorization !== null) {
            headers = headers.set('Authorization', String('Bearer ' + authorization));
        }

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            'application/json'
        ];
        let httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set("Accept", httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        let consumes: string[] = [
        ];

        return this.httpClient.get<Array<AccountReviewDetailsDTO>>(`${this.basePath}/api/review/find-all`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * Service that return a Review
     * This service return a Review by the ID
     * @param authorization 
     * @param id id
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public findById(authorization: string, id: number, observe?: 'body', reportProgress?: boolean): Observable<ReviewDTO>;
    public findById(authorization: string, id: number, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<ReviewDTO>>;
    public findById(authorization: string, id: number, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<ReviewDTO>>;
    public findById(authorization: string, id: number, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {
        if (authorization === null || authorization === undefined) {
            throw new Error('Required parameter authorization was null or undefined when calling findById.');
        }
        if (id === null || id === undefined) {
            throw new Error('Required parameter id was null or undefined when calling findById.');
        }

        let headers = this.defaultHeaders;
        if (authorization !== undefined && authorization !== null) {
            headers = headers.set('Authorization', String('Bearer ' + authorization));
        }

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            'application/json'
        ];
        let httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set("Accept", httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        let consumes: string[] = [
        ];

        return this.httpClient.get<ReviewDTO>(`${this.basePath}/api/review/find-by-id/${encodeURIComponent(String(id))}`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * Service that return a Review
     * This service return a Review by the ID
     * @param accountId accountId
     * @param authorization 
     * @param placeId placeId
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getReview(accountId: number, authorization: string, placeId: number, observe?: 'body', reportProgress?: boolean): Observable<ReviewDTO>;
    public getReview(accountId: number, authorization: string, placeId: number, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<ReviewDTO>>;
    public getReview(accountId: number, authorization: string, placeId: number, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<ReviewDTO>>;
    public getReview(accountId: number, authorization: string, placeId: number, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {
        if (accountId === null || accountId === undefined) {
            throw new Error('Required parameter accountId was null or undefined when calling getReview.');
        }
        if (authorization === null || authorization === undefined) {
            throw new Error('Required parameter authorization was null or undefined when calling getReview.');
        }
        if (placeId === null || placeId === undefined) {
            throw new Error('Required parameter placeId was null or undefined when calling getReview.');
        }

        let headers = this.defaultHeaders;
        if (authorization !== undefined && authorization !== null) {
            headers = headers.set('Authorization', String('Bearer ' + authorization));
        }

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            'application/json'
        ];
        let httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set("Accept", httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        let consumes: string[] = [
        ];

        return this.httpClient.get<ReviewDTO>(`${this.basePath}/api/review/get-review/${encodeURIComponent(String(placeId))}/${encodeURIComponent(String(accountId))}`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * Service that return a Review
     * This service return a Review by the ID
     * @param accountId accountId
     * @param authorization 
     * @param eventId eventId
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public getReviewEvent(accountId: number, authorization: string, eventId: number, observe?: 'body', reportProgress?: boolean): Observable<ReviewDTO>;
    public getReviewEvent(accountId: number, authorization: string, eventId: number, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<ReviewDTO>>;
    public getReviewEvent(accountId: number, authorization: string, eventId: number, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<ReviewDTO>>;
    public getReviewEvent(accountId: number, authorization: string, eventId: number, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {
        if (accountId === null || accountId === undefined) {
            throw new Error('Required parameter accountId was null or undefined when calling getReview.');
        }
        if (authorization === null || authorization === undefined) {
            throw new Error('Required parameter authorization was null or undefined when calling getReview.');
        }
        if (eventId === null || eventId === undefined) {
            throw new Error('Required parameter placeId was null or undefined when calling getReview.');
        }

        let headers = this.defaultHeaders;
        if (authorization !== undefined && authorization !== null) {
            headers = headers.set('Authorization', String('Bearer ' + authorization));
        }

        // to determine the Accept header
        let httpHeaderAccepts: string[] = [
            'application/json'
        ];
        let httpHeaderAcceptSelected: string | undefined = this.configuration.selectHeaderAccept(httpHeaderAccepts);
        if (httpHeaderAcceptSelected != undefined) {
            headers = headers.set("Accept", httpHeaderAcceptSelected);
        }

        // to determine the Content-Type header
        let consumes: string[] = [
        ];

        return this.httpClient.get<ReviewDTO>(`${this.basePath}/api/review/get-review-event/${encodeURIComponent(String(eventId))}/${encodeURIComponent(String(accountId))}`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

}
