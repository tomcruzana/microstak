package dev.company.guestemail;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GuestEmailAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(GuestEmailAppApplication.class, args);
	}

}
