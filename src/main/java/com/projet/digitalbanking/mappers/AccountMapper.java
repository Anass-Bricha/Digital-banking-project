package com.projet.digitalbanking.mappers;

import com.projet.digitalbanking.dtos.CurrentAccountDTO;
import com.projet.digitalbanking.dtos.CustomerDTO;
import com.projet.digitalbanking.dtos.OperationDTO;
import com.projet.digitalbanking.dtos.SavingAccountDTO;
import com.projet.digitalbanking.entities.CurrentAccount;
import com.projet.digitalbanking.entities.Customer;
import com.projet.digitalbanking.entities.Operation;
import com.projet.digitalbanking.entities.SavingAccount;

public interface AccountMapper {
    CustomerDTO fromCustomerToCustomerDTO(Customer customer);
    Customer fromCustomerDTOToCustomer(CustomerDTO customerDTO);

    SavingAccountDTO fromSavingAccountToSavingDTO(SavingAccount savingAccount);
    SavingAccount fromSavingAccountDTOToSaving(SavingAccountDTO savingAccountDTO);

    CurrentAccount fromCurrentAccountDto_To_Current(CurrentAccountDTO currentAccountDTO);
    CurrentAccountDTO fromCurrentAccount_To_CurrentDto(CurrentAccount currentAccount);

    OperationDTO fromOperation_To_OperationDto(Operation operation);

}
