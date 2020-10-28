package com.bms.loan.management.domain;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class LoanApprovalDTO {

	// can add more fields later
	@NotNull
	private Long loanApplicationId;

}
