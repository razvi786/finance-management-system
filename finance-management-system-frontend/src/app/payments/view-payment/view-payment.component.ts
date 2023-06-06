import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Payment } from 'src/app/models/Payment.model';
import { PaymentService } from 'src/app/services/payment.service';

@Component({
  selector: 'app-view-payment',
  templateUrl: './view-payment.component.html',
  styleUrls: ['./view-payment.component.css'],
})
export class ViewPaymentComponent implements OnInit {
  constructor(
    private activatedRoute: ActivatedRoute,
    private paymentService: PaymentService
  ) {}

  paymentId: string = '';

  payment: Payment = new Payment();

  ngOnInit(): void {
    let paymentId = this.activatedRoute.snapshot.paramMap.get('payment_id');
    console.log('paymentId:', paymentId);
    if (paymentId != null) {
      this.paymentId = paymentId;
      this.paymentService.getPaymentById(this.paymentId).subscribe((data) => {
        this.payment = data;
        console.log('Payment : ', this.payment);
      });
    }
  }
}
