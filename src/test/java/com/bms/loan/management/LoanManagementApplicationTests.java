package com.bms.loan.management;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.ResourceAccessException;

import com.bms.loan.management.domain.CustomerLoanApplicationDTO;
import com.bms.loan.management.domain.LoanApprovalDTO;
import com.bms.loan.management.domain.LoanTypeDTO;
import com.bms.loan.management.entities.Customer;
import com.bms.loan.management.entities.LoanAccount;
import com.bms.loan.management.entities.LoanApplication;
import com.bms.loan.management.entities.LoanType;
import com.bms.loan.management.repository.CustomerRepository;
import com.bms.loan.management.repository.LoanAccountRepository;
import com.bms.loan.management.repository.LoanApplicationRepository;
import com.bms.loan.management.repository.LoanTypeRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT, properties = { "server.port:0",
		"spring.cloud.config.discovery.enabled:false", "spring.cloud.config.enabled:false",
		"spring.profiles.active:test" })
@ExtendWith(SpringExtension.class)
@AutoConfigureMockMvc
@TestPropertySource("classpath:loan-management-junit.properties")
class LoanManagementApplicationTests {

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	CustomerRepository customerRepository;

	@MockBean
	LoanTypeRepository loanTypeRepository;

	@MockBean
	LoanAccountRepository loanAccountRepository;

	@MockBean
	LoanApplicationRepository loanApplicationRepository;

	private static final String LOAN_APPLY_URL = "/v1/loan/apply";
	private static final String APPROVE_LOAN_URL = "/v1/manage-loan/approve-loan/";
	private static final String VIEW_LOAN_APPLICATION_URL = "/v1/manage-loan/loan-applications/all";
	private static final String CREATE_LOAN_TYPE_URL = "/v1/manage-loan/create-loan-type";

	@Test
	public void testApplyLoan() throws Exception {

		CustomerLoanApplicationDTO dto = new CustomerLoanApplicationDTO();
		dto.setAmount(Double.valueOf("1050.00"));
		dto.setCustomerId(Long.valueOf(15));
		dto.setDurationInMonths(Double.valueOf("10"));
		dto.setLoanTypeId(Long.valueOf(10));
		String jsonReq = objectMapper.writeValueAsString(dto);

		Optional<Customer> customer = Optional.of(new Customer());
		doReturn(customer).when(customerRepository).findById(Mockito.any());

		Optional<LoanType> loanType = Optional.of(new LoanType());
		doReturn(loanType).when(loanTypeRepository).findById(Mockito.any());

		doReturn(new LoanApplication()).when(loanApplicationRepository).save(Mockito.any());
		mockMvc.perform(post(LOAN_APPLY_URL).contentType("application/json").content(jsonReq))
				.andExpect(status().isOk());
	}

	@Test
	public void testApplyLoanInvalidLoanType() throws Exception {

		CustomerLoanApplicationDTO dto = new CustomerLoanApplicationDTO();
		dto.setAmount(Double.valueOf("1050.00"));
		dto.setCustomerId(Long.valueOf(15));
		dto.setDurationInMonths(Double.valueOf("10"));
		dto.setLoanTypeId(Long.valueOf(10));
		String jsonReq = objectMapper.writeValueAsString(dto);

		Optional<Customer> customer = Optional.of(new Customer());
		doReturn(customer).when(customerRepository).findById(Mockito.any());

		Optional<LoanType> loanType = Optional.empty();
		doReturn(loanType).when(loanTypeRepository).findById(Mockito.any());

		doReturn(new LoanApplication()).when(loanApplicationRepository).save(Mockito.any());
		mockMvc.perform(post(LOAN_APPLY_URL).contentType("application/json").content(jsonReq))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testApplyLoanCustIdNotFound() throws Exception {

		CustomerLoanApplicationDTO dto = new CustomerLoanApplicationDTO();
		dto.setAmount(Double.valueOf("1050.00"));
		dto.setCustomerId(Long.valueOf(15));
		dto.setDurationInMonths(Double.valueOf("10"));
		dto.setLoanTypeId(Long.valueOf(10));
		String jsonReq = objectMapper.writeValueAsString(dto);

		Optional<Customer> customer = Optional.empty();
		doReturn(customer).when(customerRepository).findById(Mockito.any());

		Optional<LoanType> loanType = Optional.of(new LoanType());
		doReturn(loanType).when(loanTypeRepository).findById(Mockito.any());

		doReturn(new LoanApplication()).when(loanApplicationRepository).save(Mockito.any());
		mockMvc.perform(post(LOAN_APPLY_URL).contentType("application/json").content(jsonReq))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testApplyLoanInvalidCustId() throws Exception {

		CustomerLoanApplicationDTO dto = new CustomerLoanApplicationDTO();
		dto.setAmount(Double.valueOf("1050.00"));
		dto.setDurationInMonths(Double.valueOf("10"));
		dto.setLoanTypeId(Long.valueOf(10));
		String jsonReq = objectMapper.writeValueAsString(dto);

		mockMvc.perform(post(LOAN_APPLY_URL).contentType("application/json").content(jsonReq))
				.andExpect(status().isBadRequest());
	}

	@Test
	public void testApproveLoan() throws Exception {

		LoanApprovalDTO dto = new LoanApprovalDTO();
		dto.setLoanApplicationId(Long.valueOf(10));

		String jsonReq = objectMapper.writeValueAsString(dto);

		LoanApplication loanApplication = new LoanApplication();
		loanApplication.setAmount(Double.valueOf(1000.00));
		loanApplication.setDurationInMonths(Double.valueOf(5));
		loanApplication.setLoanType(new LoanType());
		Optional<LoanApplication> application = Optional.of(loanApplication);
		doReturn(application).when(loanApplicationRepository).findByLoanApplicationIdAndStatusFalse(Mockito.any());

		doReturn(loanApplication).when(loanApplicationRepository).save(Mockito.any());
		doReturn(new LoanAccount()).when(loanAccountRepository).save(Mockito.any());

		mockMvc.perform(post(APPROVE_LOAN_URL).contentType("application/json").content(jsonReq))
				.andExpect(status().isOk());
	}

	@Test
	public void testGetLoanApplications() throws Exception {

		doReturn(Collections.emptyList()).when(loanApplicationRepository).findAll();

		mockMvc.perform(get(VIEW_LOAN_APPLICATION_URL).contentType("application/json")).andExpect(status().isOk());
	}
	
	@Test
	public void testGetLoanApplicationsFail() throws Exception {

		doThrow(new ResourceAccessException("unavailable")).when(loanApplicationRepository).findAll();

		mockMvc.perform(get(VIEW_LOAN_APPLICATION_URL).contentType("application/json")).andExpect(status().is5xxServerError());
	}

	@Test
	public void testCreateLoanTypes() throws Exception {
		LoanTypeDTO dto = new LoanTypeDTO();
		List<String> types = new ArrayList<>();
		types.add("housing");
		dto.setTypes(types);

		String jsonReq = objectMapper.writeValueAsString(dto);
		doReturn(Collections.emptyList()).when(loanTypeRepository).saveAll(Mockito.any());
		mockMvc.perform(post(CREATE_LOAN_TYPE_URL).contentType("application/json").content(jsonReq))
				.andExpect(status().isOk());
	}

}
