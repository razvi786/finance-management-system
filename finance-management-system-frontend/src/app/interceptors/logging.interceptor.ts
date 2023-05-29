import { Injectable } from '@angular/core';
import {
  HttpRequest,
  HttpHandler,
  HttpEvent,
  HttpInterceptor,
  HttpResponse,
} from '@angular/common/http';
import { Observable, finalize, tap } from 'rxjs';

@Injectable()
export class LoggingInterceptor implements HttpInterceptor {
  constructor() {}

  intercept(
    request: HttpRequest<unknown>,
    next: HttpHandler
  ): Observable<HttpEvent<unknown>> {
    const startTime = Date.now();
    let status: string;
    return next.handle(request).pipe(
      tap(
        (event) => {
          status = '';
          if (event instanceof HttpResponse) {
            status = 'succeeded';
          }
        },
        (error) => (status = 'failed')
      ),
      finalize(() => {
        const elapsedTime = Date.now() - startTime;
        const message =
          request.method +
          ' ' +
          request.urlWithParams +
          ' ' +
          status +
          ' in ' +
          elapsedTime +
          'ms';

        this.logDetails(message);
      })
    );
  }

  private logDetails(msg: string) {
    console.log(msg);
  }
}
