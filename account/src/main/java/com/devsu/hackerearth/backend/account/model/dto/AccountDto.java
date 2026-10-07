package com.devsu.hackerearth.backend.account.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountDto {

	private Long id;
	private String number;
	private String type;
	private double initialAmount;
	private boolean isActive;
	private Long clientId;
}
