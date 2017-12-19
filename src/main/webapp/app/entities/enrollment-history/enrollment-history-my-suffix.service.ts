import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { EnrollmentHistoryMySuffix } from './enrollment-history-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class EnrollmentHistoryMySuffixService {

    private resourceUrl = SERVER_API_URL + 'api/enrollment-histories';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/enrollment-histories';

    constructor(private http: Http) { }

    create(enrollmentHistory: EnrollmentHistoryMySuffix): Observable<EnrollmentHistoryMySuffix> {
        const copy = this.convert(enrollmentHistory);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(enrollmentHistory: EnrollmentHistoryMySuffix): Observable<EnrollmentHistoryMySuffix> {
        const copy = this.convert(enrollmentHistory);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<EnrollmentHistoryMySuffix> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    search(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceSearchUrl, options)
            .map((res: any) => this.convertResponse(res));
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to EnrollmentHistoryMySuffix.
     */
    private convertItemFromServer(json: any): EnrollmentHistoryMySuffix {
        const entity: EnrollmentHistoryMySuffix = Object.assign(new EnrollmentHistoryMySuffix(), json);
        return entity;
    }

    /**
     * Convert a EnrollmentHistoryMySuffix to a JSON which can be sent to the server.
     */
    private convert(enrollmentHistory: EnrollmentHistoryMySuffix): EnrollmentHistoryMySuffix {
        const copy: EnrollmentHistoryMySuffix = Object.assign({}, enrollmentHistory);
        return copy;
    }
}
