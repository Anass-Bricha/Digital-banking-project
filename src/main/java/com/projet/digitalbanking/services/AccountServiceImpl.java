package com.projet.digitalbanking.services;

import com.projet.digitalbanking.dtos.*;
import com.projet.digitalbanking.entities.*;
import com.projet.digitalbanking.enums.TypeOperation;
import com.projet.digitalbanking.exceptions.AccountNotFoundException;
import com.projet.digitalbanking.exceptions.BalanceNotSufficientException;
import com.projet.digitalbanking.exceptions.CustomerNotFoundException;
import com.projet.digitalbanking.mappers.AccountMapper;
import com.projet.digitalbanking.repositories.AccountRepository;
import com.projet.digitalbanking.repositories.CustomerRepository;
import com.projet.digitalbanking.repositories.OperationRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional
@AllArgsConstructor
@Slf4j
public class AccountServiceImpl implements AccountService{
    private AccountMapper mapper;
    private CustomerRepository customerRepository;
    private AccountRepository accountRepository;
    private OperationRepository operationRepository;


    @Override
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        Customer customer = mapper.fromCustomerDTOToCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return mapper.fromCustomerToCustomerDTO(savedCustomer);
    }

    @Override
    public CurrentAccountDTO saveCurrentAccount(double initialBalance, double overDraft, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("Customer not found");
        CurrentAccount currentAccount=new CurrentAccount();
        currentAccount.setId(UUID.randomUUID().toString());
        currentAccount.setCreatedAt(new Date());
        currentAccount.setBalance(initialBalance);
        currentAccount.setOverDraft(overDraft);
        currentAccount.setCustomer(customer);
        CurrentAccount savedAccount = accountRepository.save(currentAccount);
        return mapper.fromCurrentAccount_To_CurrentDto(savedAccount);
    }

    @Override
    public SavingAccountDTO saveSavingAccount(double initialBalance, double interestRate, Long customerId) throws CustomerNotFoundException {
        Customer customer=customerRepository.findById(customerId).orElse(null);
        if(customer==null)
            throw new CustomerNotFoundException("Customer not found");
        SavingAccount savingAccount=new SavingAccount();
        savingAccount.setId(UUID.randomUUID().toString());
        savingAccount.setCreatedAt(new Date());
        savingAccount.setBalance(initialBalance);
        savingAccount.setInterestRate(interestRate);
        savingAccount.setCustomer(customer);
        SavingAccount savedAccount = accountRepository.save(savingAccount);
        return mapper.fromSavingAccountToSavingDTO(savedAccount);
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        List<Customer> customerList = customerRepository.findAll();
        List<CustomerDTO> customerDTOList = customerList.stream()
                .map(customer -> mapper.fromCustomerToCustomerDTO(customer))
                .collect(Collectors.toList());

        return customerDTOList;
    }

    @Override
    public AccountDTO getAccount(String accountId) throws AccountNotFoundException {
        Account Account=accountRepository.findById(accountId)
                .orElseThrow(()->new AccountNotFoundException("Account not found"));
        if(Account instanceof SavingAccount){
            SavingAccount savingAccount= (SavingAccount) Account;
            return mapper.fromSavingAccountToSavingDTO(savingAccount);
        } else {
            CurrentAccount currentAccount= (CurrentAccount) Account;
            return mapper.fromCurrentAccount_To_CurrentDto(currentAccount);
        }
    }

    @Override
    public void debit(String accountId, double amount, String description) throws AccountNotFoundException, BalanceNotSufficientException {
        Account bankAccount=accountRepository.findById(accountId)
                .orElseThrow(()->new AccountNotFoundException("BankAccount not found"));
        if(bankAccount.getBalance()<amount)
            throw new BalanceNotSufficientException("Balance not sufficient");
        Operation accountOperation=new Operation();
        accountOperation.setType(TypeOperation.DEBIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setAccount(bankAccount);
        operationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()-amount);
        accountRepository.save(bankAccount);
    }

    @Override
    public void credit(String accountId, double amount, String description) throws AccountNotFoundException {
        Account bankAccount=accountRepository.findById(accountId)
                .orElseThrow(()->new AccountNotFoundException("BankAccount not found"));
        Operation accountOperation=new Operation();
        accountOperation.setType(TypeOperation.CREDIT);
        accountOperation.setAmount(amount);
        accountOperation.setDescription(description);
        accountOperation.setOperationDate(new Date());
        accountOperation.setAccount(bankAccount);
        operationRepository.save(accountOperation);
        bankAccount.setBalance(bankAccount.getBalance()+amount);
        accountRepository.save(bankAccount);
    }

    @Override
    public void transfer(String accountIdSource, String accountIdDestination, double amount) throws AccountNotFoundException, BalanceNotSufficientException {
        debit(accountIdSource,amount,"Transfer to "+accountIdDestination);
        credit(accountIdDestination,amount,"Transfer from "+accountIdSource);
    }

    @Override
    public List<AccountDTO> AccountList() {
        List<Account> accounts = accountRepository.findAll();
        List<AccountDTO> accountDTOList = accounts.stream().map(account -> {
            if (account instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) account;
                return mapper.fromSavingAccountToSavingDTO(savingAccount);
            } else {
                CurrentAccount currentAccount = (CurrentAccount) account;
                return mapper.fromCurrentAccount_To_CurrentDto(currentAccount);
            }
        }).collect(Collectors.toList());
        return accountDTOList;
    }

    @Override
    public CustomerDTO getCustomer(Long customerId) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() -> new CustomerNotFoundException("Customer Not found"));
        return mapper.fromCustomerToCustomerDTO(customer);
    }

    @Override
    public CustomerDTO updateCustomer(CustomerDTO customerDTO) {
        log.info("Saving new Customer");
        Customer customer=mapper.fromCustomerDTOToCustomer(customerDTO);
        Customer savedCustomer = customerRepository.save(customer);
        return mapper.fromCustomerToCustomerDTO(savedCustomer);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        customerRepository.deleteById(customerId);
    }

    @Override
    public List<OperationDTO> accountHistory(String accountId) {
        List<Operation> accountOperations = operationRepository.findByAccountId(accountId);
        return accountOperations.stream().map(op->mapper.fromOperation_To_OperationDto(op)).collect(Collectors.toList());
    }

    @Override
    public AccountHistoryDTO getAccountHistory(String accountId, int page, int size) throws AccountNotFoundException {
        Account bankAccount=accountRepository.findById(accountId).orElse(null);
        if(bankAccount==null) throw new AccountNotFoundException("Account not Found");
        Page<Operation> accountOperations = operationRepository.findByAccountIdOrderByOperationDate(accountId, PageRequest.of(page, size));
        AccountHistoryDTO accountHistoryDTO=new AccountHistoryDTO();
        List<OperationDTO> accountOperationDTOS = accountOperations.getContent().stream().map(op -> mapper.fromOperation_To_OperationDto(op)).collect(Collectors.toList());
        accountHistoryDTO.setOperationDTOS(accountOperationDTOS);
        accountHistoryDTO.setAccountId(bankAccount.getId());
        accountHistoryDTO.setBalance(bankAccount.getBalance());
        accountHistoryDTO.setCurrentPage(page);
        accountHistoryDTO.setPageSize(size);
        accountHistoryDTO.setTotalPages(accountOperations.getTotalPages());
        return accountHistoryDTO;
    }

    @Override
    public List<CustomerDTO> searchCustomers(String keyword) {
        //List<Customer> customers=customerRepository.searchCustomer(keyword);
        List<Customer> customers = customerRepository.findCustomerByNameContaining(keyword);
        List<CustomerDTO> customerDTOList = customers.stream().map(customer -> mapper.fromCustomerToCustomerDTO(customer)).collect(Collectors.toList());
        return customerDTOList;
    }



    @Override
    public List<AccountDTO> CustomerAccountList(Long id) {
        Customer customer = customerRepository.findById(id).get();
        List<Account> accounts = customer.getAccounts();
        if(accounts == null){
            throw new RuntimeException("its null");
        }
        List<AccountDTO> accountDTOS = accounts.stream().map(account -> {
            if (account instanceof SavingAccount) {
                SavingAccount savingAccount = (SavingAccount) account;
                return mapper.fromSavingAccountToSavingDTO(savingAccount);
            } else {
                CurrentAccount currentAccount = (CurrentAccount) account;
                return mapper.fromCurrentAccount_To_CurrentDto(currentAccount);
            }
        }).collect(Collectors.toList());
        return accountDTOS;
    }
}
