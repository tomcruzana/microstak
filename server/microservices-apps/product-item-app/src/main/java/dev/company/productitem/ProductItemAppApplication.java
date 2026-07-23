package dev.company.productitem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProductItemAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProductItemAppApplication.class, args);
	}

}
