package com.bms.loan.management.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bms.loan.management.domain.LoanAccountDTO;
import com.bms.loan.management.domain.LoanApprovalDTO;
import com.bms.loan.management.domain.LoanTypeDTO;
import com.bms.loan.management.entities.LoanApplication;
import com.bms.loan.management.entities.LoanType;
import com.bms.loan.management.service.LoanManagementService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("v1/manage-loan")
@Slf4j
public class ManagementController {

	@Value("${loan.application.accepted}")
	private String loanApplicationAcceptedMessage;

	@Autowired
	LoanManagementService service;

	@PostMapping("/create-loan-type")
	public List<LoanType> create(@RequestBody LoanTypeDTO loanTypeDto) {

		log.info("Loan type creattion req received {}", loanTypeDto);

		return service.create(loanTypeDto);

	}

	@GetMapping("/loan-applications/all")
	public List<LoanApplication> loanApplications() {

		return service.getAllLoanApplications();

	}

	@PostMapping("/approve-loan/")
	public LoanAccountDTO approveLoan(@RequestBody @Valid LoanApprovalDTO approvalDto) {

		log.info("Loan approval req received {}", approvalDto);

		return service.approve(approvalDto);

	}
}
