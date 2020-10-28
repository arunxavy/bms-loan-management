package com.bms.loan.management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableDiscoveryClient
@EnableSwagger2
public class LoanManagementApplication {

	public static void main(String[] args) {// NOSONAR
		SpringApplication.run(LoanManagementApplication.class, args);// NOSONAR
	}

}
