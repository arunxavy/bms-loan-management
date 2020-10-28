package com.bms.loan.management.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bms.loan.management.entities.LoanApplication;

public interface LoanApplicationRepository extends JpaRepository<LoanApplication, Long>{

	Optional<LoanApplication> findByLoanApplicationIdAndStatusFalse(Long loanApplicationId);

}
