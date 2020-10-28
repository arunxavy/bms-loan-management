package com.bms.loan.management.entities;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import lombok.Getter;
import lombok.Setter;

@Entity
@Table(schema = "bms", name = "loan_account")
@Getter
@Setter
@DynamicUpdate
public class LoanAccount implements Serializable {

	private static final long serialVersionUID = -1193731224051796659L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long loanAccountId;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "customer_id", nullable = false)
	private Customer customer;

	@JsonManagedReference
	@OneToOne
	@JoinColumn(name = "loan_application_id", nullable = false)
	private LoanApplication loanApplication;

	@JsonManagedReference
	@ManyToOne
	@JoinColumn(name = "loan_type_id", nullable = false)
	private LoanType loanType;

	private Double principal;

	private Double durationInMonths;

	private Double interestRate;

	private Double totalLoanAmount;
	
	private boolean status;

	@JsonIgnore
	private Date createdDate;

	@JsonIgnore
	private Date updatedDate;

}
