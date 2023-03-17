import { Component, inject, Input, OnInit } from '@angular/core';
import { Payment } from "../../models/payment.model";
import { PaymentsService } from "../../services/payments.service";
import { catchError, EMPTY, empty, Observable } from "rxjs";
import { FormBuilder, FormGroup, Validators } from "@angular/forms";
import { CurrencyList } from "../../models/types";
import { MessageService } from 'primeng/api';
import { HttpResponse, HttpStatusCode } from '@angular/common/http';
import { ParseError } from '@angular/compiler';



@Component({
  selector: 'app-payments-list',
  templateUrl: './payments-list.component.html',
  styleUrls: ['./payments-list.component.css']
})
export class PaymentsListComponent implements OnInit {
  paymentService = inject(PaymentsService);
  paymentsList$?: Observable<Payment[]>;
  display = false;

  selectedCurrency: any = null;
  currencies = CurrencyList.concat();


  ngOnInit() {
    this.getAll();
  }

  deletePayment(id: string) {
    var numberValue = Number(id);
    this.paymentService.deletePayment(numberValue).subscribe({
      next: () => { this.getAll() },
      error: (err: HttpResponse<HttpStatusCode>) => { this.messageService.add({ severity: 'error', summary: 'Error'}) }
    })
  }

  getAll() {
    this.paymentsList$ = this.paymentService.getAll().pipe(catchError((err: HttpResponse<HttpStatusCode>) => {
      this.messageService.add({ severity: 'error', summary: 'Error'}); 
      throw err;
    }))
  }

  updatePayment(id: string, payment: Payment) {
    var numberValue = Number(id);
    this.paymentService.updatePayment(numberValue, payment).subscribe({
      next: () => { this.getAll() },
      error: (err: HttpResponse<HttpStatusCode>) => { this.messageService.add({ severity: 'error', summary: 'Error' }) }
    })
  }

  createNewPayment(payment: Payment) {
    this.paymentService.createNewPayment(payment).subscribe({
      next: () => { this.getAll() },
      error: (err: HttpResponse<HttpStatusCode>) => { this.messageService.add({ severity: 'error', summary: 'Error'}) }
    })
  }

  public form: FormGroup = this.formBuilder.group({
    payerEmail: ['', [Validators.required, Validators.email]],
    currency: ['', [Validators.required]],
    amount: ['0', [Validators.required, Validators.min(0)]]
  });

  constructor(
    private formBuilder: FormBuilder,
    private messageService: MessageService
  ) { }


}
