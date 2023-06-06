import { Component, OnInit } from '@angular/core';
import { Payment } from 'src/app/models/Payment.model';
import { PaymentService } from 'src/app/services/payment.service';

@Component({
  selector: 'app-all-payments',
  templateUrl: './all-payments.component.html',
  styleUrls: ['./all-payments.component.css'],
})
export class AllPaymentsComponent implements OnInit {
  constructor(private paymentService: PaymentService) {}

  payments: Payment[] = [];

  dtOptions: DataTables.Settings = {};

  ngOnInit(): void {
    this.paymentService.getAllPayments().subscribe((data) => {
      this.payments = this.sortAscendingOrder(data);
      console.log('Payments : ', this.payments);
    });
    this.dtOptions = {
      pagingType: 'full_numbers',
      pageLength: 10,
    };
  }

  sortAscendingOrder(payments: Payment[]): Payment[] {
    return payments.sort(
      (payment1: Payment, payment2: Payment) =>
        new Date(payment2.createdDatetime).getTime() -
        new Date(payment1.createdDatetime).getTime()
    );
  }
}
