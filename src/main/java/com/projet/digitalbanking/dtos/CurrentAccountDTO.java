package com.projet.digitalbanking.dtos;

import com.projet.digitalbanking.enums.AccountStatus;
import lombok.Data;

import java.util.Date;

@Data
public class CurrentAccountDTO extends AccountDTO {
    private String id;
    private double balance;
    private Date createdAt;
    private AccountStatus status;
    //private CustomerDTO customerDTO;
    private double overDraft;
    private final String type = "CA";
}
