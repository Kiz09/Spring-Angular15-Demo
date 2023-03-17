import {Component, inject, Input, OnInit} from '@angular/core';
import { Payment } from "../../models/payment.model";
import {PaymentsService} from "../../services/payments.service";
import {Observable, Subscription} from "rxjs";
import {FormBuilder, FormGroup, Validators} from "@angular/forms";
import {CurrencyList} from "../../models/types";
// import {MessageService} from "primeng/api";


@Component({
  selector: 'app-payments-list',
  templateUrl: './payments-list.component.html',
  styleUrls: ['./payments-list.component.css']
})
export class PaymentsListComponent implements OnInit{
  paymentService=inject(PaymentsService);
  paymentsList$? : Observable<Payment[]>;
  payment$? : Subscription;

  selectedCurrency: any = null;
  currencies = CurrencyList.concat();


  ngOnInit() {
    this.getAll();
  }

  deletePayment(id : string){
    var numberValue = Number(id);
    this.payment$ = this.paymentService.deletePayment(numberValue).subscribe(()=>this.getAll());
    this.ngOnInit();
  }

  getAll() {
    this.paymentsList$ = this.paymentService.getAll();
  }

  updatePayment(id : string, payment : Payment){
    var numberValue = Number(id);
    this.payment$ = this.paymentService.updatePayment(numberValue, payment).subscribe(()=>this.getAll());
  }

  createNewPayment(payment : Payment){
    this.payment$ = this.paymentService.createNewPayment(payment).subscribe(()=>this.getAll());

  }

  public form: FormGroup = this.formBuilder.group({
    payerEmail: ['', [Validators.required]],
    currency: ['', [Validators.required]],
    amount: ['', [Validators.required]]
  });

  constructor(
    private formBuilder: FormBuilder
    // private messageService: MessageService
  ) {  }


}
