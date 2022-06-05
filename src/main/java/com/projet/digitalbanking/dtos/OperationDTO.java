package com.projet.digitalbanking.dtos;

import com.projet.digitalbanking.enums.TypeOperation;
import lombok.Data;

import java.util.Date;

@Data
public class OperationDTO {
    private Long id;
    private Date operationDate;
    private double amount;
    private TypeOperation type;
    private String description;
}

