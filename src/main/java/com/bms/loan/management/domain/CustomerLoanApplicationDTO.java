package com.bms.loan.management.domain;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class CustomerLoanApplicationDTO {

	@NotNull(message = "Customer id is mandatory")
	private Long customerId;

	private Long loanTypeId;

	private Double amount;

	private Double durationInMonths;
}
