package com.projet.digitalbanking.web;


import com.projet.digitalbanking.dtos.*;
import com.projet.digitalbanking.exceptions.AccountNotFoundException;
import com.projet.digitalbanking.exceptions.BalanceNotSufficientException;
import com.projet.digitalbanking.services.AccountService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@AllArgsConstructor
@CrossOrigin("*")
public class AccountRestController {
    private AccountService accountService;



    @GetMapping("/accounts/{accountId}")
    public AccountDTO getAccount(@PathVariable String accountId) throws AccountNotFoundException {
        return accountService.getAccount(accountId);
    }
    @GetMapping("/accounts")
    public List<AccountDTO> listAccounts(){
        return accountService.AccountList();
    }

    @GetMapping("/accounts/{accountId}/operations")
    public List<OperationDTO> getHistory(@PathVariable String accountId){
        return accountService.accountHistory(accountId);
    }

    @GetMapping("/accounts/{accountId}/pageOperations")
    public AccountHistoryDTO getAccountHistory(
            @PathVariable String accountId,
            @RequestParam(name="page",defaultValue = "0") int page,
            @RequestParam(name="size",defaultValue = "5")int size) throws AccountNotFoundException {
        return accountService.getAccountHistory(accountId,page,size);
    }
    @PostMapping("/accounts/debit")
    public DebitDTO debit(@RequestBody DebitDTO debitDTO) throws AccountNotFoundException, BalanceNotSufficientException {
        this.accountService.debit(debitDTO.getAccountId(),debitDTO.getAmount(),debitDTO.getDescription());
        return debitDTO;
    }
    @PostMapping("/accounts/credit")
    public CreditDTO credit(@RequestBody CreditDTO creditDTO) throws AccountNotFoundException {
        this.accountService.credit(creditDTO.getAccountId(),creditDTO.getAmount(),creditDTO.getDescription());
        return creditDTO;
    }
    @PostMapping("/accounts/transfer")
    public void transfer(@RequestBody TransferRequestDTO transferRequestDTO) throws AccountNotFoundException, BalanceNotSufficientException {
        this.accountService.transfer(
                transferRequestDTO.getAccountSource(),
                transferRequestDTO.getAccountDestination(),
                transferRequestDTO.getAmount());
    }
}
