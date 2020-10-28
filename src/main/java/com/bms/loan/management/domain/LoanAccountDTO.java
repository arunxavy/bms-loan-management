package com.bms.loan.management.domain;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoanAccountDTO {

	private Long loanAccountId;

	private Double principal;

	private Double durationInMonths;

	private Double interestRate;

	private Double totalLoanAmount;
}
