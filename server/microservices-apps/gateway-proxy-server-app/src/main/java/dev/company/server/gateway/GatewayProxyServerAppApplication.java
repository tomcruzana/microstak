package dev.company.server.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayProxyServerAppApplication {

	// see all gatway routes : http://localhost:9091/actuator/gateway/routes
	// sample request through the gateway: http://localhost:8765/companyapi/company_info
	public static void main(String[] args) {
		SpringApplication.run(GatewayProxyServerAppApplication.class, args);
	}

}
