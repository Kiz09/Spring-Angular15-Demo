import { Injectable } from '@angular/core';
import {HttpClient} from "@angular/common/http";
import {Observable} from "rxjs";
import {Payment} from "../models/payment.model";

const baseUrl = 'http://localhost:8080/payments';

@Injectable({
  providedIn: 'root'
})
export class PaymentsService {

  constructor(private http: HttpClient) { }

  getAll(): Observable<Payment[]> {
    return this.http.get<Payment[]>(baseUrl+"/");
  }

  getById(id: any): Observable<any>{
    return this.http.get(baseUrl+'/${id}')
  }

  createNewPayment(payment: Payment): Observable<any>{
    return this.http.post<Payment>(baseUrl+'/', payment);
  }

  updatePayment(id: any, payment: Payment): Observable<any>{
    return this.http.put<Payment>(baseUrl+'/'+id, payment);
  }

  deletePayment(id: any): Observable<any>{
    return this.http.delete<Payment>(baseUrl+'/'+id);
  }

}
