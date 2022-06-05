package com.projet.digitalbanking.services;


import com.projet.digitalbanking.entities.Account;
import com.projet.digitalbanking.entities.CurrentAccount;
import com.projet.digitalbanking.entities.SavingAccount;
import com.projet.digitalbanking.repositories.AccountRepository;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
@AllArgsConstructor
@NoArgsConstructor
public class BankService {
    private AccountRepository accountRepository;

    public void consulter(){
        Account account=
                accountRepository.findById("0b36be78-8d5d-446b-9f20-37eadc9d3c3b").orElse(null);
        if(account!=null) {
            System.out.println("*****************************");
            System.out.println(account.getId());
            System.out.println(account.getBalance());
            System.out.println(account.getStatus());
            System.out.println(account.getCreatedAt());
            System.out.println(account.getCustomer().getName());
            System.out.println(account.getClass().getSimpleName());
            if (account instanceof CurrentAccount) {
                System.out.println("Over Draft=>" + ((CurrentAccount) account).getOverDraft());
            } else if (account instanceof SavingAccount) {
                System.out.println("Rate=>" + ((SavingAccount) account).getInterestRate());
            }
            account.getAccountOperations().forEach(op -> {
                System.out.println(op.getType() + "\t" + op.getOperationDate() + "\t" + op.getAmount());
            });
        }
    }
}
