package com.projet.digitalbanking.web;

import com.projet.digitalbanking.dtos.AccountDTO;
import com.projet.digitalbanking.dtos.CustomerDTO;
import com.projet.digitalbanking.entities.Account;
import com.projet.digitalbanking.entities.CurrentAccount;
import com.projet.digitalbanking.entities.Customer;
import com.projet.digitalbanking.entities.SavingAccount;
import com.projet.digitalbanking.exceptions.CustomerNotFoundException;
import com.projet.digitalbanking.mappers.AccountMapper;
import com.projet.digitalbanking.repositories.AccountRepository;
import com.projet.digitalbanking.repositories.CustomerRepository;
import com.projet.digitalbanking.services.AccountService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@Slf4j
@CrossOrigin("*")
public class CustomerRestController {
    private AccountService accountService;
    private AccountMapper accountMapper;
    private AccountRepository accountRepository;
    private CustomerRepository customerRepository;



    @GetMapping("/customers")
    public List<CustomerDTO> customers(){
        return accountService.listCustomers();
    }



    @GetMapping("/customers/search")
    public List<CustomerDTO> searchCustomers(@RequestParam(name = "keyword",defaultValue = "") String keyword){
        return accountService.searchCustomers(keyword);
    }


    @GetMapping("/customers/{id}")
    public CustomerDTO getCustomer(@PathVariable(name = "id") Long customerId) throws CustomerNotFoundException {
        return accountService.getCustomer(customerId);
    }
    @PostMapping("/customers")
    public CustomerDTO saveCustomer(@RequestBody CustomerDTO customerDTO){
        return accountService.saveCustomer(customerDTO);
    }
    @PutMapping("/customers/{customerId}")
    public CustomerDTO updateCustomer(@PathVariable Long customerId, @RequestBody CustomerDTO customerDTO){
        customerDTO.setId(customerId);
        return accountService.updateCustomer(customerDTO);
    }
    @DeleteMapping("/customers/{id}")
    public void deleteCustomer(@PathVariable Long id){
        accountService.deleteCustomer(id);
    }


    @GetMapping(path = "/customer/{id}/accounts")
    public List<AccountDTO> customerAccounts(@PathVariable Long id){
        return accountService.CustomerAccountList(id);
    }
}
