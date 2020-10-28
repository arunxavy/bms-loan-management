package com.bms.loan.management.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bms.loan.management.domain.CustomerLoanApplicationDTO;
import com.bms.loan.management.entities.LoanApplication;
import com.bms.loan.management.service.LoanService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("v1/loan")
@Slf4j
public class LoanController {

	@Value("${loan.application.accepted}")
	private String loanApplicationAcceptedMessage;

	@Autowired
	LoanService service;

	@PostMapping("/apply")
	public LoanApplication applyLoan(@Valid @RequestBody CustomerLoanApplicationDTO loanApplication) {

		log.info("Loan application received {}", loanApplication);

		LoanApplication application = service.customerLoanApplication(loanApplication);

		log.info(loanApplicationAcceptedMessage, application);

		return application;

	}
}
