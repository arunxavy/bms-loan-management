package com.bms.loan.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.bms.loan.management.entities.Customer;

public interface CustomerRepository extends JpaRepository<Customer, Long> {

}
