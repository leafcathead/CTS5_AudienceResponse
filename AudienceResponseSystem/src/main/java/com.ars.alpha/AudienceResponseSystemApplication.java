package com.ars.alpha;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class AudienceResponseSystemApplication {

	public static void main(String[] args) {

		SpringApplication.run(AudienceResponseSystemApplication.class, args);
	}

}
