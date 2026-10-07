package com.devsu.hackerearth.backend.account.model.dto;

import java.util.Date;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionDto {

    private Long id;
    private Date date;
    private String type;
    private double amount;
    private double balance;
    private Long accountId;

}