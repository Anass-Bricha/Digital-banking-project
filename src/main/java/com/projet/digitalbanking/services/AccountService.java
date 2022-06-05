package com.projet.digitalbanking.services;

import com.projet.digitalbanking.dtos.*;
import com.projet.digitalbanking.exceptions.AccountNotFoundException;
import com.projet.digitalbanking.exceptions.BalanceNotSufficientException;
import com.projet.digitalbanking.exceptions.CustomerNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface AccountService {
    CustomerDTO saveCustomer(CustomerDTO customerDTO);
    CurrentAccountDTO saveCurrentAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException;
    SavingAccountDTO saveSavingAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException;
    List<CustomerDTO> listCustomers();
    AccountDTO getAccount(String accountId) throws AccountNotFoundException;
    void debit(String accountId, double amount, String description) throws AccountNotFoundException, BalanceNotSufficientException;
    void credit(String accountId, double amount, String description) throws AccountNotFoundException;
    void transfer(String accountIdSource, String accountIdDestination, double amount) throws AccountNotFoundException, BalanceNotSufficientException;

    List<AccountDTO> AccountList();

    CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException;

    CustomerDTO updateCustomer(CustomerDTO customerDTO);

    void deleteCustomer(Long customerId);

    List<OperationDTO> accountHistory(String accountId);

    AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws AccountNotFoundException;

    List<CustomerDTO> searchCustomers(String keyword);

    List<AccountDTO> CustomerAccountList(Long id);

}
