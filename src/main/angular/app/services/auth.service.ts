import {Injectable} from '@angular/core';
import {HttpClient, HttpErrorResponse, HttpHeaders} from '@angular/common/http';
import {Observable, Subject, throwError} from 'rxjs';
import { sha256} from 'js-sha256';
import {catchError} from "rxjs/operators";

const AUTH_API = 'http://localhost:8888/auth/';

const httpOptions = {
    headers: new HttpHeaders({'Content-Type': 'application/json'})
};

@Injectable({
    providedIn: 'root'
})
export class AuthService {
    constructor(private http: HttpClient) {
    }

    public error$: Subject<string> = new Subject<string>()



    login(email: string, password: string): Observable<any> {

            password = sha256(password)
            return this.http.post(AUTH_API + 'login', {
                email,
                password
            }, httpOptions).pipe(
                catchError(this.handleError.bind(this))
            );
    }

    private handleError(error: HttpErrorResponse)
    {
        if (error.status === 401 || error.status === 403)
        {
            this.error$.next('Wrong password or email!')
        }

        return throwError(error)
    }
}