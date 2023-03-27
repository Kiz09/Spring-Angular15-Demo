import {Currencies} from "./types";


export interface Payment {

  id: any;
  createdDate: string;
  payerEmail: string;
  status: string;
  currency: Currencies;
  amount: number;
  paidDate: string;

}

