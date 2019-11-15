// src/app/services/token-interceptor.service.ts

import { Injectable } from '@angular/core';
import { HttpRequest, HttpHandler,HttpEvent, HttpInterceptor } from '@angular/common/http';
import { Observable } from 'rxjs/Observable';
import {AuthService} from "../auth/services/auth.service";
import {Router} from "@angular/router";

@Injectable()
export class TokenInterceptorService implements HttpInterceptor {

  constructor(public auth: AuthService,private router: Router) {
  }

  intercept(request: HttpRequest<any>, next: HttpHandler):
  Observable<HttpEvent<any>> {

    return next.handle(request)
      .catch(error => {
        if(error.status == 401) {
          this.router.navigate(['/', 'login']);
        }
        return Observable.throw(error);
      });
  }
}
