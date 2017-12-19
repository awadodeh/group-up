import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { AddressMySuffix } from './address-my-suffix.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AddressMySuffixService {

    private resourceUrl = SERVER_API_URL + 'api/addresses';
    private resourceSearchUrl = SERVER_API_URL + 'api/_search/addresses';

    constructor(private http: Http) { }

    create(address: AddressMySuffix): Observable<AddressMySuffix> {
        const copy = this.convert(address);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(address: AddressMySuffix): Observable<AddressMySuffix> {
        const copy = this.convert(address);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AddressMySuffix> {
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
     * Convert a returned JSON object to AddressMySuffix.
     */
    private convertItemFromServer(json: any): AddressMySuffix {
        const entity: AddressMySuffix = Object.assign(new AddressMySuffix(), json);
        return entity;
    }

    /**
     * Convert a AddressMySuffix to a JSON which can be sent to the server.
     */
    private convert(address: AddressMySuffix): AddressMySuffix {
        const copy: AddressMySuffix = Object.assign({}, address);
        return copy;
    }
}
