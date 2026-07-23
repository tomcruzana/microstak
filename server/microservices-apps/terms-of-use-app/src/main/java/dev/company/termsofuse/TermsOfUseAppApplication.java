package dev.company.termsofuse;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class TermsOfUseAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(TermsOfUseAppApplication.class, args);
	}

}
