package com.projet.digitalbanking.mappers;

import com.projet.digitalbanking.dtos.CurrentAccountDTO;
import com.projet.digitalbanking.dtos.CustomerDTO;
import com.projet.digitalbanking.dtos.OperationDTO;
import com.projet.digitalbanking.dtos.SavingAccountDTO;
import com.projet.digitalbanking.entities.CurrentAccount;
import com.projet.digitalbanking.entities.Customer;
import com.projet.digitalbanking.entities.Operation;
import com.projet.digitalbanking.entities.SavingAccount;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class AccountMapperImpl implements AccountMapper {

    private ModelMapper modelMapper = new ModelMapper();

    @Override
    public CustomerDTO fromCustomerToCustomerDTO(Customer customer) {
        CustomerDTO customerDTO = modelMapper.map(customer,CustomerDTO.class);
        return customerDTO;
    }

    @Override
    public Customer fromCustomerDTOToCustomer(CustomerDTO customerDTO) {
        Customer customer = modelMapper.map(customerDTO,Customer.class);
        return customer;
    }

    @Override
    public SavingAccountDTO fromSavingAccountToSavingDTO(SavingAccount savingAccount) {
        return modelMapper.map(savingAccount,SavingAccountDTO.class);
    }

    @Override
    public SavingAccount fromSavingAccountDTOToSaving(SavingAccountDTO savingAccountDTO) {
        return modelMapper.map(savingAccountDTO,SavingAccount.class);
    }

    @Override
    public CurrentAccount fromCurrentAccountDto_To_Current(CurrentAccountDTO currentAccountDTO) {
        return modelMapper.map(currentAccountDTO,CurrentAccount.class);
    }

    @Override
    public CurrentAccountDTO fromCurrentAccount_To_CurrentDto(CurrentAccount currentAccount) {
        return modelMapper.map(currentAccount,CurrentAccountDTO.class);
    }

    @Override
    public OperationDTO fromOperation_To_OperationDto(Operation operation) {
        return modelMapper.map(operation,OperationDTO.class);
    }
}
