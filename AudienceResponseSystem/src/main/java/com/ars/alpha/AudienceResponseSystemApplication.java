package com.ars.alpha;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = { "com.ars.alpha" })
//@ImportResource("classpath:beans.xml")
@EntityScan("com.ars.alpha.model")
@EnableJpaRepositories("com.ars.alpha.dao")
public class AudienceResponseSystemApplication {


	public static void main(String[] args) {

		SpringApplication.run(AudienceResponseSystemApplication.class, args);
	}

}
