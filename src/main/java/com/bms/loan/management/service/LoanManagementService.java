package com.bms.loan.management.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bms.loan.management.domain.LoanAccountDTO;
import com.bms.loan.management.domain.LoanApprovalDTO;
import com.bms.loan.management.domain.LoanTypeDTO;
import com.bms.loan.management.entities.LoanAccount;
import com.bms.loan.management.entities.LoanApplication;
import com.bms.loan.management.entities.LoanType;
import com.bms.loan.management.exceptions.InvalidInputException;
import com.bms.loan.management.repository.LoanAccountRepository;
import com.bms.loan.management.repository.LoanApplicationRepository;
import com.bms.loan.management.repository.LoanTypeRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class LoanManagementService {

	@Autowired
	LoanTypeRepository loanTypeRepository;

	@Autowired
	LoanApplicationRepository loanApplicationRepository;

	@Autowired
	LoanAccountRepository loanAccountRepository;

	public List<LoanType> create(LoanTypeDTO loanTypeDto) {
		List<LoanType> loanTypes = new ArrayList<>();
		Date date = new Date();
		loanTypeDto.getTypes().forEach(type -> {
			LoanType loanType = new LoanType();
			loanType.setType(type);
			loanType.setStatus(true);
			loanType.setCreatedDate(date);
			loanType.setUpdatedDate(date);
			loanTypes.add(loanType);
		});

		return loanTypeRepository.saveAll(loanTypes);
	}

	public List<LoanApplication> getAllLoanApplications() {
		return loanApplicationRepository.findAll();
	}

	@Transactional
	public LoanAccountDTO approve(LoanApprovalDTO approvalDto) {
		Optional<LoanApplication> application = loanApplicationRepository
				.findByLoanApplicationIdAndStatusFalse(approvalDto.getLoanApplicationId());
		if (!application.isPresent()) {

			throw new InvalidInputException("Loan application number is invalid");
		}
		Date date = new Date();
		LoanApplication loanApplication = application.get();
		loanApplication.setStatus(true); // approved
		loanApplication.setUpdatedDate(date);
		loanApplicationRepository.save(loanApplication);

		LoanAccount loanAccount = new LoanAccount();
		loanAccount.setCustomer(loanApplication.getCustomer());
		loanAccount.setDurationInMonths(loanApplication.getDurationInMonths());
		loanAccount.setInterestRate(getInterestRate(loanApplication.getLoanType()));
		loanAccount.setLoanApplication(loanApplication);
		loanAccount.setCreatedDate(date);
		loanAccount.setUpdatedDate(date);
		loanAccount.setLoanType(loanApplication.getLoanType());
		loanAccount.setPrincipal(loanApplication.getAmount());
		loanAccount.setTotalLoanAmount(calculateTotal(loanAccount));
		loanAccount.setStatus(true);

		// principal amount can be deposited to savings account
		// negative total loan amount can be tracked in a separate account
		LoanAccount saved = loanAccountRepository.save(loanAccount);
		LoanAccountDTO accountDTO = new LoanAccountDTO();
		BeanUtils.copyProperties(saved, accountDTO);
		return accountDTO;
	}

	private Double calculateTotal(LoanAccount loanAccount) {
		double amount = loanAccount.getPrincipal()
				* Math.pow(1 + (loanAccount.getInterestRate() / 100), loanAccount.getDurationInMonths()/12);
		return Double.valueOf(amount);
	}

	private Double getInterestRate(LoanType loanType) {
		log.info(loanType.getType());
		return 7.0;
	}

}
