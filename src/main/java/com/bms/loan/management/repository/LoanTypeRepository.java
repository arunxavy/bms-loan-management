package com.bms.loan.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bms.loan.management.entities.LoanType;

public interface LoanTypeRepository extends JpaRepository<LoanType, Long>{

}
