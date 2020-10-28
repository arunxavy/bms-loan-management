package com.bms.loan.management.service;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import com.bms.loan.management.domain.CustomerLoanApplicationDTO;
import com.bms.loan.management.entities.Customer;
import com.bms.loan.management.entities.LoanApplication;
import com.bms.loan.management.entities.LoanType;
import com.bms.loan.management.exceptions.InvalidInputException;
import com.bms.loan.management.repository.CustomerRepository;
import com.bms.loan.management.repository.LoanApplicationRepository;
import com.bms.loan.management.repository.LoanTypeRepository;

@Service
@RefreshScope
public class LoanService {

	
	@Value("${response.invalid.customer.id}")
	private String invalidCustomerId;
	
	@Value("${response.invalid.loan.type.id}")
	private String invalidLoanTypeId;
	
	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	LoanTypeRepository loanTypeRepository;

	@Autowired
	LoanApplicationRepository loanApplicationRepository;

	public LoanApplication customerLoanApplication(CustomerLoanApplicationDTO loanApplication) {

		Optional<Customer> customer = customerRepository.findById(loanApplication.getCustomerId());

		if (!customer.isPresent()) {
			throw new InvalidInputException(invalidCustomerId);
		}

		Optional<LoanType> loanType = loanTypeRepository.findById(loanApplication.getLoanTypeId());

		if (!loanType.isPresent()) {
			throw new InvalidInputException(invalidLoanTypeId);
		}

		LoanApplication application = new LoanApplication();
		application.setAmount(loanApplication.getAmount());
		application.setCustomer(customer.get());
		application.setLoanType(loanType.get());
		application.setStatus(false);
		application.setDurationInMonths(loanApplication.getDurationInMonths());
		Date date = new Date();
		application.setCreatedDate(date);
		application.setUpdatedDate(date);
		return loanApplicationRepository.save(application);

	}

}
