export interface AccountDetails {
  id:                   string;
  balance:              number;
  currentPage:          number;
  totalPages:           number;
  pageSize:             number;
  type:                 string;
  accountOperationDTOS: AccountOperation[];
}

export interface  AccountOperation {
  id:            number;
  operationDate: Date;
  amount:        number;
  type:          string;
  description:   string;
}
