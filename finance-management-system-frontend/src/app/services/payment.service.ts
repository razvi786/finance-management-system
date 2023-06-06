import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { environment } from 'src/environments/environment';
import { Payment } from '../models/Payment.model';

@Injectable({
  providedIn: 'root',
})
export class PaymentService {
  path: string = environment.apiUrl + '/pms';

  constructor(private http: HttpClient) {}

  getAllPayments(): Observable<Payment[]> {
    return this.http.get<Payment[]>(this.path + '/payments');
  }

  getPaymentById(id: string): Observable<Payment> {
    return this.http.get<Payment>(this.path + '/payment/' + id);
  }

  createPayment(payment: Payment): Observable<Payment> {
    return this.http.post<Payment>(this.path + '/payment', payment);
  }

  updatePayment(id: string, payment: Payment): Observable<Payment> {
    return this.http.put<Payment>(this.path + '/payment/' + id, payment);
  }
}
