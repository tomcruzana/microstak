package dev.company.faq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class FaqAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FaqAppApplication.class, args);
	}

}
