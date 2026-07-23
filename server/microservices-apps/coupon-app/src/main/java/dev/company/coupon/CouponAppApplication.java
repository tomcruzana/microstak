package dev.company.coupon;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class CouponAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(CouponAppApplication.class, args);
	}

}
