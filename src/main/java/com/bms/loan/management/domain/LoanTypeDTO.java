package com.bms.loan.management.domain;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class LoanTypeDTO {

	private List<String> types;
}
