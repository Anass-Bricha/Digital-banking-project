package com.projet.digitalbanking;

import com.projet.digitalbanking.dtos.AccountDTO;
import com.projet.digitalbanking.dtos.CurrentAccountDTO;
import com.projet.digitalbanking.dtos.CustomerDTO;
import com.projet.digitalbanking.dtos.SavingAccountDTO;
import com.projet.digitalbanking.entities.*;
import com.projet.digitalbanking.enums.AccountStatus;
import com.projet.digitalbanking.enums.TypeOperation;
import com.projet.digitalbanking.exceptions.CustomerNotFoundException;
import com.projet.digitalbanking.mappers.AccountMapper;
import com.projet.digitalbanking.repositories.AccountRepository;
import com.projet.digitalbanking.repositories.CustomerRepository;
import com.projet.digitalbanking.repositories.OperationRepository;
import com.projet.digitalbanking.services.AccountService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Stream;

@SpringBootApplication
public class DigitalBankingApplication {

    public static void main(String[] args) {
        SpringApplication.run(DigitalBankingApplication.class, args);
    }



    /*@Bean
    CommandLineRunner commandLineRunner(AccountService accountService){
        return args -> {
            Stream.of("Hanan","Amine","Anass","Hamid").forEach(name->{
                CustomerDTO customer=new CustomerDTO();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                accountService.saveCustomer(customer);
            });
            accountService.listCustomers().forEach(customer->{
                try {
                    accountService.saveCurrentAccount(Math.random()*90000,9000,customer.getId());
                    accountService.saveSavingAccount(Math.random()*120000,5.5,customer.getId());

                } catch (CustomerNotFoundException e) {
                    e.printStackTrace();
                }
            });
            List<AccountDTO> Accounts = accountService.AccountList();
            for (AccountDTO Account:Accounts){
                for (int i = 0; i <10 ; i++) {
                    String accountId;
                    if(Account instanceof SavingAccountDTO){
                        accountId=((SavingAccountDTO) Account).getId();
                    } else{
                        accountId=((CurrentAccountDTO) Account).getId();
                    }
                    accountService.credit(accountId,10000+Math.random()*120000,"Credit");
                    accountService.debit(accountId,1000+Math.random()*9000,"Debit");
                }
            }
        };
    }

    @Bean
    CommandLineRunner start(CustomerRepository customerRepository,
                            AccountRepository accountRepository,
                            OperationRepository OperationRepository){
        return args -> {
            Stream.of("Hassan","Yassine","Aicha").forEach(name->{
                Customer customer=new Customer();
                customer.setName(name);
                customer.setEmail(name+"@gmail.com");
                customerRepository.save(customer);
            });
            customerRepository.findAll().forEach(cust->{
                CurrentAccount currentAccount=new CurrentAccount();
                currentAccount.setId(UUID.randomUUID().toString());
                currentAccount.setBalance(Math.random()*90000);
                currentAccount.setCreatedAt(new Date());
                currentAccount.setStatus(AccountStatus.CREATED);
                currentAccount.setCustomer(cust);
                currentAccount.setOverDraft(9000);
                accountRepository.save(currentAccount);

                SavingAccount savingAccount=new SavingAccount();
                savingAccount.setId(UUID.randomUUID().toString());
                savingAccount.setBalance(Math.random()*90000);
                savingAccount.setCreatedAt(new Date());
                savingAccount.setStatus(AccountStatus.CREATED);
                savingAccount.setCustomer(cust);
                savingAccount.setInterestRate(5.5);
                accountRepository.save(savingAccount);

            });
            accountRepository.findAll().forEach(acc->{
                for (int i = 0; i <10 ; i++) {
                    Operation Operation=new Operation();
                    Operation.setOperationDate(new Date());
                    Operation.setAmount(Math.random()*12000);
                    Operation.setType(Math.random()>0.5? TypeOperation.DEBIT: TypeOperation.CREDIT);
                    Operation.setAccount(acc);
                    OperationRepository.save(Operation);
                }

            });
        };



    }


    //@Bean
    CommandLineRunner start(AccountRepository accountRepository){
        return args -> {
            List<Account> accountList = accountRepository.findAccountsByCustomer_Id(4L);
            if(accountList.isEmpty()){
                System.out.println("is empty");
            }else{
                accountList.forEach(account ->
                        System.out.println(account.getId()+"\n"+account.getBalance()));
            }
        };
    }*/

}
