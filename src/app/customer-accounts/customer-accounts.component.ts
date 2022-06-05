import { Component, OnInit } from '@angular/core';
import {ActivatedRoute, Router} from "@angular/router";
import {Customer} from "../model/customer.model";
import {AccountsService} from "../service/accounts.service";
import {catchError, map, Observable, throwError} from "rxjs";
import {AccountDetails} from "../model/account.model";

@Component({
  selector: 'app-customer-accounts',
  templateUrl: './customer-accounts.component.html',
  styleUrls: ['./customer-accounts.component.css']
})
export class CustomerAccountsComponent implements OnInit {
  customerAccounts! : Observable<AccountDetails[]>
  customerId! : number ;
  customer! : Customer;
  errorMessage!: string;
  constructor(private route : ActivatedRoute, private router :Router,private accountService:AccountsService) {
    this.customer=this.router.getCurrentNavigation()?.extras.state as Customer;
  }

  ngOnInit(): void {
    this.customerId = this.route.snapshot.params['id'];
    this.handleCustomerAccounts(this.customerId);
  }

  handleCustomerAccounts(id:number){
    this.customerAccounts = this.accountService.customerAccounts(id).pipe(
      catchError(err => {
        this.errorMessage=err.message;
        return throwError(err);
      })
    )

  }


  handleOperations(id:string) {
    this.router.navigateByUrl("/account/"+id);
  }

}
