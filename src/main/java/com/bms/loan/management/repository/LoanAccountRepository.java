package com.bms.loan.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bms.loan.management.entities.LoanAccount;

public interface LoanAccountRepository extends JpaRepository<LoanAccount, Long>{

}
