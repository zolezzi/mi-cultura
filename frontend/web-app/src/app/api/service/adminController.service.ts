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

import { BasicResponse } from '../model/basicResponse';
import { UserDTO } from '../model/userDTO';
import { environment } from 'src/environments/environment';
import { BASE_PATH, COLLECTION_FORMATS }                     from '../variables';
import { Configuration }                                     from '../configuration';


@Injectable()
export class AdminControllerService {

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
        if(environment.production){
            this.basePath = environment.apiUrl;
        }else{
            this.basePath = environment.apiUrl;
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
     * This service delete a User
     * Delete a User, if it doesn&#39;t find it throw an exception
     * @param authorization 
     * @param id id
     * @param userId userId
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public deleteById(authorization: string, id: number, userId: number, observe?: 'body', reportProgress?: boolean): Observable<BasicResponse>;
    public deleteById(authorization: string, id: number, userId: number, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<BasicResponse>>;
    public deleteById(authorization: string, id: number, userId: number, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<BasicResponse>>;
    public deleteById(authorization: string, id: number, userId: number, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {
        if (authorization === null || authorization === undefined) {
            throw new Error('Required parameter authorization was null or undefined when calling deleteById.');
        }
        if (id === null || id === undefined) {
            throw new Error('Required parameter id was null or undefined when calling deleteById.');
        }
        if (userId === null || userId === undefined) {
            throw new Error('Required parameter userId was null or undefined when calling deleteById.');
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

        return this.httpClient.delete<BasicResponse>(`${this.basePath}/api/delete/${encodeURIComponent(String(userId))}/${encodeURIComponent(String(id))}`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * Service that returns all users
     * This service returns all users load
     * @param authorization 
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public findAll(authorization: string, observe?: 'body', reportProgress?: boolean): Observable<Array<UserDTO>>;
    public findAll(authorization: string, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<Array<UserDTO>>>;
    public findAll(authorization: string, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<Array<UserDTO>>>;
    public findAll(authorization: string, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {
        if (authorization === null || authorization === undefined) {
            throw new Error('Required parameter authorization was null or undefined when calling findAll.');
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

        return this.httpClient.get<Array<UserDTO>>(`${this.basePath}/api/find-all`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * Service that return a User
     * This service return a User by the ID
     * @param authorization 
     * @param id id
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public findById1(authorization: string, id: number, observe?: 'body', reportProgress?: boolean): Observable<UserDTO>;
    public findById1(authorization: string, id: number, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<UserDTO>>;
    public findById1(authorization: string, id: number, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<UserDTO>>;
    public findById1(authorization: string, id: number, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {
        if (authorization === null || authorization === undefined) {
            throw new Error('Required parameter authorization was null or undefined when calling findById1.');
        }
        if (id === null || id === undefined) {
            throw new Error('Required parameter id was null or undefined when calling findById1.');
        }

        let headers = this.defaultHeaders;
        if (authorization !== undefined && authorization !== null) {
            headers = headers.set('Authorization', String(authorization));
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

        return this.httpClient.get<UserDTO>(`${this.basePath}/api/find-by-id/${encodeURIComponent(String(id))}`,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

    /**
     * This service update a User
     * Update a User, if it doesn&#39;t find it throw an exception
     * @param authorization 
     * @param user user
     * @param userId userId
     * @param observe set whether or not to return the data Observable as the body, response or events. defaults to returning the body.
     * @param reportProgress flag to report request and response progress.
     */
    public update(authorization: string, user: UserDTO, userId: number, observe?: 'body', reportProgress?: boolean): Observable<UserDTO>;
    public update(authorization: string, user: UserDTO, userId: number, observe?: 'response', reportProgress?: boolean): Observable<HttpResponse<UserDTO>>;
    public update(authorization: string, user: UserDTO, userId: number, observe?: 'events', reportProgress?: boolean): Observable<HttpEvent<UserDTO>>;
    public update(authorization: string, user: UserDTO, userId: number, observe: any = 'body', reportProgress: boolean = false ): Observable<any> {
        if (authorization === null || authorization === undefined) {
            throw new Error('Required parameter authorization was null or undefined when calling update.');
        }
        if (user === null || user === undefined) {
            throw new Error('Required parameter user was null or undefined when calling update.');
        }
        if (userId === null || userId === undefined) {
            throw new Error('Required parameter userId was null or undefined when calling update.');
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
            'application/json'
        ];
        let httpContentTypeSelected:string | undefined = this.configuration.selectHeaderContentType(consumes);
        if (httpContentTypeSelected != undefined) {
            headers = headers.set("Content-Type", httpContentTypeSelected);
        }

        return this.httpClient.put<UserDTO>(`${this.basePath}/api/update/${encodeURIComponent(String(userId))}`,
            user,
            {
                withCredentials: this.configuration.withCredentials,
                headers: headers,
                observe: observe,
                reportProgress: reportProgress
            }
        );
    }

}
